package safeobject.guardapplication;

import android.app.PendingIntent;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.File;

import app_interface.SendNotificationAPI;
import model.Notification;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData().size() > 0) {

            notify(remoteMessage);
        }
    }

    private void notify(RemoteMessage remoteMessage){
        Intent intent = new Intent(this, Notification_Detail.class);
        Bundle bundle = new Bundle();
        bundle.putString("NotiCamera","Cầu thang lầu 1");
        bundle.putString("NotiDate","test");
        bundle.putString("NotiImage",remoteMessage.getData().get("images"));
        intent.putExtras(bundle);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Notification_Detail.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent notifyPendingIntent = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_ONE_SHOT);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setContentTitle(remoteMessage.getNotification().getTitle() +"      "+ remoteMessage.getData().get("dateTime"))
                .setContentText(remoteMessage.getData().get("cameraID")+" có vật nguy hiểm")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, mBuilder.build());
    }

}
