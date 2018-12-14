package safeobject.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

import safeobject.model.NotificationDTO;
import safeobject.view.Activity.NotificationDetailActivity;
import safeobject.view.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData().size() > 0) {

            notify(remoteMessage);
        }
    }

    private void notify(RemoteMessage remoteMessage) {
        String NOTIFICATION_CHANEL_ID = "SafeObject";
        Intent intent = new Intent(this, NotificationDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("NotiCamera", "Cầu thang lầu 1");
        bundle.putString("NotiDate", "test");
        bundle.putString("NotiImage", remoteMessage.getData().get("images"));
        intent.putExtras(bundle);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationDetailActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent notifyPendingIntent = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(remoteMessage.getNotification().getTitle() + "      " + remoteMessage.getData().get("dateTime"))
                .setContentText(remoteMessage.getData().get("cameraID") + " có vật nguy hiểm")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, mBuilder.build());

        NotificationDTO notification = new NotificationDTO();
        notification.setImageURL(remoteMessage.getData().get("images"));
        notification.setCameraID((long) 1);
        notification.setTime(Calendar.getInstance().getTimeInMillis());
        safeobject.Manager.NotificationManager.createNotification(notification);
    }

}
