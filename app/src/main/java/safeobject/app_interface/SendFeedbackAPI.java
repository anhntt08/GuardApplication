package safeobject.app_interface;

import retrofit2.http.Query;
import safeobject.model.FeedbackDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendFeedbackAPI {
        @POST( "/sendFeedback" )
        @Headers( "Content-Type: application/json" )
        Call<String> sendFeedback(@Query( "Authorization" ) String token,@Body FeedbackDTO feedbackDTO);

}



