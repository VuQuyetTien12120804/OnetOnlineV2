package com.example.onetonline.data;

import com.example.onetonline.presentation.model.ChangePassRequest;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserAPI {
    @GET("users")
    Call<List<userRanking>> getTopUser();

    @GET("user")
    Call<User> getUser(@Header("Authorization") String Authorization);

    @POST("user")
    Call<PostResponse> addUser(@Body SignupRequest signupRequest);

    @POST("auth")
    Call<token> login(@Body LoginRequest loginRequest);

    @PATCH("sync")
    Call<Void> updateUser(@Body User user, @Header("Authorization") String Authorization);

    @PATCH("user")
    Call<Void> changePass(@Body ChangePassRequest changePassRequest);
}