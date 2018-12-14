package safeobject.app_interface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import safeobject.model.NotificationFeedbackDTO;
import safeobject.model.NotificationMobileDTO;

public interface NotificationFeedbackAPI {
    @Headers( "Content-Type: application/json" )
    @POST( "/create-notification-feedback" )
    Call<String> createNotiFeedback(@Query( "Authorization" ) String token, @Body NotificationFeedbackDTO notificationFeedbackDTO);

    @Headers( "Content-Type: application/json" )
    @GET( "/get-notification-feedback" )
    Call<NotificationFeedbackDTO> getNotiFeedback(@Query( "Authorization" ) String token, @Body NotificationMobileDTO noti);

}
