package safeobject.guardapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.LibraryGlideModule;

import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import model.Notification;

public class Notification_Detail extends AppCompatActivity  {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__detail);



        Notification notification = (Notification) getIntent().getSerializableExtra("Notification_detail");
        Log.d("AnhNTT",notification.toString());

        imageView = findViewById(R.id.ImageView_NotificationDetail);
        textView = findViewById(R.id.textView_NotificationDetail);
        Log.d("AnhNTT onCreate: ",notification.getImageURL());
//        getDownloadURLImage("images/"+notification.getImageURL());
    }

//    public Bitmap StringToBitMap(String encodedString){
//        try{
//            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
//            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        }catch(Exception e){
//            e.getMessage();
//            return null;
//        }
//    }

    private void getDownloadURLImage(String name){
        Log.d("AnhNTT,getDownloadURLImage",name);
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

    private void loadImage(Uri uri){
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
}
