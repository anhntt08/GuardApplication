package app_interface;

import model.FeedbackDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendFeedbackAPI {
    @POST( "sendFeedback" )
    @Headers( "Content-Type: application/json" )
    Call<String> sendFeedback(@Body FeedbackDTO feedbackDTO);

}



