package app_interface;

import java.util.List;

import model.Notification;
import model.NotificationDTO;
import model.NotificationList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SendNotificationAPI {
    @POST("sendNotification")
    @Headers( "Content-Type: application/json" )
    Call<Notification> uploadNotification(@Body NotificationDTO noti);

    @GET("getAllListNotification")
    @Headers("Content-Type: application/json")
    Call<List<Notification>> getListNotificationByStatus();

    @POST("updateNotification")
    @Headers("Content-Type: application/json")
    Call<Boolean> updateNotification(@Body Notification notification);
}
