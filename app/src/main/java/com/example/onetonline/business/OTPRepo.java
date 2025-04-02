package com.example.onetonline.business;

import com.example.onetonline.presentation.model.userOTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPRepo {
    private OTP_API otpApi;

    public OTPRepo() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        otpApi = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8001/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(OTP_API.class);
    }

    public interface SendCallBack {
        void onSuccess();

        void onFailure(String err);
    }

    public interface ResendCallBack {
        void onSuccess();

        void onFailure(String err);
    }

    public interface VerifyCallBack {
        void onSuccess();

        void onFailure(String err);
    }

    public void sendOTP(userOTP otp, final SendCallBack callBack) {
        otpApi.send_otp(otp).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess();
                } else callBack.onFailure(String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public void resendOTP(userOTP otp, final ResendCallBack callBack) {
        otpApi.resend_otp(otp).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess();
                } else {
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public void verifyOTP(String emailOrName, String otp, final VerifyCallBack callBack) {
        otpApi.verify_otp(emailOrName, otp).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess();
                } else {
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure("Network error: " + t.getMessage());
            }
        });
    }
}
