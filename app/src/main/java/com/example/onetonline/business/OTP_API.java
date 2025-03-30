package com.example.onetonline.business;

import com.example.onetonline.presentation.model.userOTP;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OTP_API {
    @POST("otp")
    Call<Void> send_otp(@Body userOTP otp);

    @DELETE("otp")
    Call<Void> verify_otp(@Query("email") String email, @Query("otp") String otp);

    @PATCH("otp")
    Call<Void> resend_otp(@Body userOTP otp);
}
