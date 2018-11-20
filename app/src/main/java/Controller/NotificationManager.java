package Controller;

import android.util.Log;
import android.widget.Toast;

import app_interface.SendNotificationAPI;
import model.Notification;
import model.NotificationDTO;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationManager {
    SendNotificationAPI sendNotificationAPI;

    public NotificationManager() {
        this.sendNotificationAPI = RetrofitClientInstance.getRetrofitInstanc().create(SendNotificationAPI.class);
    }

    public void uploadNotification(NotificationDTO notification) {
//        SendNotificationAPI sendNotificationAPI = RetrofitClientInstance.getRetrofitInstanc().create(SendNotificationAPI.class);
        Call<Notification> call = sendNotificationAPI.uploadNotification(notification);

        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                Notification noti = response.body();
                if (noti != null) {
                    Log.d("AnhNTT, success uploadNotification", noti.toString());
                }

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Log.d("AnhNTT, error uploadNotification ", t.getMessage());
            }
        });

    }

    public void updateNotification(Notification noti){

    }
}
