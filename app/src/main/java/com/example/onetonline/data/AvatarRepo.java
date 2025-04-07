package com.example.onetonline.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AvatarRepo {
    private AvatarAPI avatarAPI;

    public AvatarRepo() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        avatarAPI = new Retrofit.Builder()
                .baseUrl("http://192.168.170.193:8002/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(AvatarAPI.class);
    }

    public interface UploadCallBack {
        void onSuccess();
        void onFailure(String err);
    }

    public interface GetCallBack {
        void onSuccess(Bitmap bitmap);
        void onFailure(String err);
    }

    public interface DeleteCallBack {
        void onSuccess();
        void onFailure(String err);
    }

    public void uploadAvatar(String id, File avatar, final UploadCallBack callBack){
        String mimeType = avatar.getName().endsWith(".png") ? "image/png" : "image/jpeg";
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), avatar);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("file", avatar.getName(), requestFile);
        avatarAPI.uploadAvatar(id, avatarPart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        callBack.onFailure("Error " + response.code() + ": " + errorBody);
                    } catch (IOException e) {
                        callBack.onFailure("Error " + response.code() + ": Failed to read error body");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public void getAvatar(String id, final GetCallBack callBack){
        avatarAPI.getAvatar(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        byte[] fileBytes = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.length);
                        if (bitmap != null) {
                            callBack.onSuccess(bitmap);
                        } else {
                            callBack.onFailure("Failed to decode avatar");
                        }
                    } catch (IOException e) {
                        callBack.onFailure("IO error: " + e.getMessage());
                    }
                }
                else {
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public void deleteAvatar(String id, final DeleteCallBack callBack){
        avatarAPI.deleteAvatar(id).enqueue(new Callback<Void>() {
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
}
