package safeobject.guardapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import model.Notification;

public class Notification_Detail extends AppCompatActivity {
    ImageView imageView;
    TextView txtLocation, txtTime;
    Button btnApprove, btnReject;
    Camera camera;

    private final int CAMERA_PIC_REQUEST = 8;
    private final int MY_CAMERA_PERMISSION_CODE = 0;
    private File output = null;
    private Uri imageUri;
    private String imageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__detail);


        final Notification notification = (Notification) getIntent().getSerializableExtra("Notification_detail");
        Log.d("AnhNTT", notification.toString());

        imageView = findViewById(R.id.ImageView_NotificationDetail);
        txtLocation = findViewById(R.id.notiDetail_txtLocation);
        txtTime = findViewById(R.id.notiDetail_txtTime);
        btnApprove = findViewById(R.id.notiDetail_btnApprove);
        btnReject = findViewById(R.id.notiDetail_btnDeny);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.setStatus(1);// set reject
            }
        });
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notification.setStatus(2);//set done
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

        txtLocation.setText(notification.getCamera().getCameraLocation());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(notification.getDateTime());
        txtTime.setText(currentDateandTime);

        getDownloadURLImage("images/" + notification.getImageURL());

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
                // Handle any errors
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
                        imageView.setImageBitmap(resource);
                    }
                });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap thumbnail = null;
            try {
//                thumbnail = MediaStore.Images.Media.getBitmap(
//                                    getContentResolver(), imageUri);
                thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                thumbnail = RotateBitmap(thumbnail, 90);
                Drawable d = new BitmapDrawable(getResources(), thumbnail);
                imageView.setImageDrawable(d);
                imageurl = getRealPathFromURI(imageUri);
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


}
