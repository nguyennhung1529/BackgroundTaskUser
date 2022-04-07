package com.example.neu_simplebackgroundtask;

import java.util.ArrayList;
import com.example.neu_simplebackgroundtask.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    String token = "e6c4cfb6ed8d550d9f79af16c20ad499a9dcd0fd4f8659452440bd5fef926dcf";
    @GET("users?access-token=" + token)
    Call<ArrayList<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUsersByID(@Path("id") int id);

    @POST("users?access-token=" + token)
    Call<User> addUser(@Body() User user);

    @DELETE("users/{id}?access-token="+ token)
    Call<Void> deleteUser(@Path("id") int id);

    @PUT("users/{id}?access-token="+ token)
    Call<Void> updateUser(@Path("id") int id, @Body() User user);

}
