package safeobject.view.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import safeobject.Manager.TokenManager;
import safeobject.app_interface.NotificationFeedbackAPI;
import safeobject.model.NotificationMobileDTO;
import safeobject.model.NotificationFeedbackDTO;
import safeobject.network.RetrofitClientInstance;
import safeobject.view.R;

import static safeobject.view.Activity.NotificationDetailActivity.RotateBitmap;

public class NotificationFeedbackActivity extends AppCompatActivity {
    private NotificationMobileDTO noti;
    private EditText edtxtDescription;
    private ImageView imageView;
    private TokenManager tokenManager;
    private String accessToken;
    private boolean showFeedback = true;
    private RelativeLayout layout;
    private Uri imageUri;
    //    private String imageurl;
    private final int CAMERA_PIC_REQUEST = 8;
    private boolean isDone = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_feedback);

        edtxtDescription = findViewById(R.id.notiFeedback_etxtDescription);
        imageView = findViewById(R.id.notiFeedback_imageView);

        tokenManager = new TokenManager(this);
        accessToken = tokenManager.getTokenFromSession();

        noti = (NotificationMobileDTO) getIntent().getSerializableExtra("NotificationFeedbackActivity");

        NotificationFeedbackAPI service = RetrofitClientInstance.getRetrofitInstanc()
                .create(NotificationFeedbackAPI.class);

        Call<NotificationFeedbackDTO> callUpload = service.getNotiFeedback(accessToken, noti);
        callUpload.enqueue(new Callback<NotificationFeedbackDTO>() {
            @Override
            public void onResponse(Call<NotificationFeedbackDTO> call, Response<NotificationFeedbackDTO> response) {
                NotificationFeedbackDTO notiFeedback = response.body();
                if (notiFeedback != null) {
                    isDone = true;
                    edtxtDescription.setText(notiFeedback.getDescription());
                    Bitmap thumbnail = null;
                    try {
                        thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        thumbnail = RotateBitmap(thumbnail, 90);
                        Drawable d = new BitmapDrawable(getResources(), thumbnail);
                        imageView.setImageDrawable(d);
                        layout.setBackground(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationFeedbackDTO> call, Throwable t) {

            }
        });


        layout = findViewById(R.id.notiFeedback_layout);
        if(!isDone){
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);
                }
            });
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                thumbnail = RotateBitmap(thumbnail, 90);
                Drawable d = new BitmapDrawable(getResources(), thumbnail);
                imageView.setImageDrawable(d);
                layout.setBackground(null);
                noti.setImage_url(getRealPathFromURI(imageUri));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (noti.getStatus() == 0) {
            getMenuInflater().inflate(R.menu.feedback, menu);
            menu.findItem(R.id.action_bar_sendFeedback).setVisible(showFeedback);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bar_sendFeedback) {


            NotificationFeedbackDTO notificationFeedbackDTO = getNotifeedback();
//            final String accessToken = tokenManager.getTokenFromSession();
            final NotificationFeedbackAPI notificationFeedbackAPI = RetrofitClientInstance.getRetrofitInstanc()
                    .create(NotificationFeedbackAPI.class);
            Log.d("AnhNTT, id", notificationFeedbackDTO.getNotificationId() + "");
            Call<String> getAccess = notificationFeedbackAPI.createNotiFeedback(accessToken, notificationFeedbackDTO);
            getAccess.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.code() == 200) {
//                        showToast("create success");
//                    } else if (response.code() == 401) {
//                        showToast("Session timed out. Please re-login");
//                    } else {
//                        showToast("Some errors have occurred: " + response.body());
//                    }
                    Intent intent = new Intent(NotificationFeedbackActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                    showToast("" + t.getMessage());
                    Intent intent = new Intent(NotificationFeedbackActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });


        }
        return super.onOptionsItemSelected(item);
    }

    private NotificationFeedbackDTO getNotifeedback() {
        String descrip = edtxtDescription.getText().toString().trim();
        NotificationFeedbackDTO notificationFeedbackDTO = new NotificationFeedbackDTO();
        notificationFeedbackDTO.setDescription(descrip);
        notificationFeedbackDTO.setImageURL(imageUri.getPath());
        notificationFeedbackDTO.setNotificationId(noti.getId());
        return notificationFeedbackDTO;
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_LONG).show();
    }
}
