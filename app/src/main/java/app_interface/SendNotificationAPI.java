package app_interface;

import java.util.List;

import model.Notification;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendNotificationAPI {
    @POST("/sendNotification/")
    @Headers( "Content-Type: application/json" )
    Call<Notification> uploadImage(@Body Notification noti);
}
