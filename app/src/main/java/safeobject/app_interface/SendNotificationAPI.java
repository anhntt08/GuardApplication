package safeobject.app_interface;

import java.util.List;

import retrofit2.http.Query;
import safeobject.model.NotificationMobileDTO;
import safeobject.model.NotificationDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendNotificationAPI {
    @POST("/sendNotification")
    @Headers( "Content-Type: application/json" )
    Call<NotificationMobileDTO> uploadNotification(@Body NotificationDTO noti);

    @POST("/updateNotification")
    @Headers("Content-Type: application/json")
    Call<Boolean> updateNotification(@Body NotificationMobileDTO notificationMobileDTO);

    @GET("/getAllNotification")
    @Headers("Content-Type: application/json")
    Call<List<NotificationMobileDTO>> getListNotification(@Query( "Authorization" ) String token );

    @Headers({"Accept: application/json"})
    @POST("/camera/createNotification")
    Call<String> createNotification(@Body NotificationDTO notificationDTO);
}
