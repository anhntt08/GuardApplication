package safeobject.Manager;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import safeobject.app_interface.SendNotificationAPI;
import safeobject.model.NotificationDTO;
import safeobject.network.RetrofitClientInstance;

public class NotificationManager {
    public static void createNotification(NotificationDTO noti) {
        SendNotificationAPI service = RetrofitClientInstance.getRetrofitInstanc()
                .create(SendNotificationAPI.class);

        Call<String> callUpload = service.createNotification(noti);
        callUpload.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    Log.d("Anhntt, onResponse", "200");
                } else if (response.code() == 401) {
                    Log.d("Anhntt, onResponse", "401");
                } else if (response.code() == 404) {
                    Log.d("Anhntt, onResponse", "404");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("AnhNTT, error connect javasystem", t.getMessage());

            }
        });
    }
}
