package systemsltd.basicstructure.utils;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Raziuddin.Shaikh on 7/29/2016.
 */

public interface RestApi {

    // SMS verification
    @POST("/verifyCode")
    Call<ResponseBody> verifyCode(@Body RequestBody input);

    // Login
    @POST("/login")
    Call<ResponseBody> login(@Body RequestBody input);

    // FB Login
    @POST("/getFbUser")
    Call<ResponseBody> getFbUser(@Body RequestBody input);




   /*@GET("/login")
    Call<ResponseBody> getWheatherReport();

    @GET("/search/users")
    Call<GitResult> getUsersNamedTom(@Query("q") String name);

    @POST("/user/create")
    Call<Item> createUser(@Body String name, @Body String email);

    @PUT("/user/{id}/update")
    Call<Item> updateUser(@Path("id") String id , @Body Item user);*/
}
