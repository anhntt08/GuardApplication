package safeobject.view.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import safeobject.model.NotificationMobileDTO;
import safeobject.view.R;

public class NotificationDetailActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnApprove, btnReject;
    Camera camera;

    private Uri imageUri;
    private String imageurl;
    private ProgressBar progressBar;
    private NotificationMobileDTO noti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__detail);


        noti = (NotificationMobileDTO) getIntent().getSerializableExtra("Notification_detail");

        imageView = findViewById(R.id.ImageView_NotificationDetail);
        progressBar = findViewById(R.id.notiDetail_progressBar);

        btnApprove = findViewById(R.id.notiDetail_btnApprove);
//        btnReject = findViewById(R.id.notiDetail_btnDeny);
//        btnReject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                noti.setStatus(1);// set reject
//                Intent intent = new Intent(v.getContext(), HomeActivity.class);
//                startActivity(intent);
//            }
//        });
        if(noti.getStatus() == 1){
            btnApprove.setText("Đã hoàn thành");
            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NotificationDetailActivity.this, NotificationFeedbackActivity.class);
                    intent.putExtra("NotificationFeedbackActivity", noti);
                    startActivity(intent);
                }
            });
        }
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                noti.setStatus(2);//set done
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE, "New Picture");
//                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
//                imageUri = getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, CAMERA_PIC_REQUEST);

                Intent intent = new Intent(NotificationDetailActivity.this, NotificationFeedbackActivity.class);
                intent.putExtra("NotificationFeedbackActivity", noti);
                startActivity(intent);
            }
        });

//        getSupportActionBar().setTitle(noti.getCamera().getCameraLocation());

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String currentDateandTime = sdf.format(notification.getDateTime());
//        txtTime.setText(currentDateandTime);

        getDownloadURLImage("images/" + noti.getImage_url());

    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void getDownloadURLImage(String name) {
        Log.d("AnhNTT,getDownloadURLImage", name);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        Uri uri = null;
        storageRef.child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                loadImage(uri);
                Log.d("AnhNTT, uri", uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
            }
        });

    }

    private void loadImage(Uri uri) {
        Glide.with(this)
                .asBitmap()
                .load(uri.toString())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(resource);
                    }
                });

    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAMERA_PIC_REQUEST) {
//            Bitmap thumbnail = null;
//            try {
//                thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                thumbnail = RotateBitmap(thumbnail, 90);
//                Drawable d = new BitmapDrawable(getResources(), thumbnail);
//                imageView.setImageDrawable(d);
////                imageurl = getRealPathFromURI(imageUri);
////                Log.d("AnhNTT, imageurl: ", imageurl);
//                noti.setImage_url(getRealPathFromURI(imageUri));
//                btnReject.setVisibility(View.GONE);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }




}
