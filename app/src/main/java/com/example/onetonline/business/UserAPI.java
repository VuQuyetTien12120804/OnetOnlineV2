package com.example.onetonline.business;

import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.SignupRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {
    @GET("user")
    Call<User> getUser(@Header("Authorization") String Authorization);

    @POST("user")
    Call<PostResponse> addUser(@Body SignupRequest signupRequest);

    @POST("auth")
    Call<token> login(@Body LoginRequest loginRequest);
}