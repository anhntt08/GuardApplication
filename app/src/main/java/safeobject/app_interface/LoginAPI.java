package safeobject.app_interface;

import retrofit2.http.GET;
import retrofit2.http.Query;
import safeobject.model.Auth;
import safeobject.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginAPI {
    @Headers( "Content-Type: application/json" )
    @POST( "/token/generate-token" )
    Call<Auth> login(@Body User user );

    @GET( "/android-demo-auth" )
    Call<String> testAccess( @Query( "Authorization" ) String token, @Body String test );
}
