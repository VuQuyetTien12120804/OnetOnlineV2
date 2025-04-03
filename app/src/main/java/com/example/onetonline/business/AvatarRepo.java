package com.example.onetonline.business;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.onetonline.MainActivity;
import com.example.onetonline.data.AvatarManager;
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
                .baseUrl("http://10.0.2.2:8002/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(AvatarAPI.class);
    }

    public interface UploadCallBack {
        void onSuccess();

        void onFailure(String err);
    }

    public interface GetCallBack {
        void onSuccess();

        void onFailure(String err);
    }

    public interface DeleteCallBack {
        void onSuccess();

        void onFailure(String err);
    }

    public void uploadAvatar(String id, File avatar, final UploadCallBack callBack){
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), avatar);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", avatar.getName(), requestFile);
        avatarAPI.uploadAvatar(id, avatarPart).enqueue(new Callback<Void>() {
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

    public void getAvatar(String id, final GetCallBack callBack, Context context){
        avatarAPI.getAvatar(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    try {
                        // Lấy dữ liệu bytes
                        byte[] fileBytes = responseBody.bytes();
                        // Chuyển thành Bitmap (nếu cần)
                        Bitmap bitmap = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.length);
                        // Lưu vào Internal Storage
                        AvatarManager.saveImage(context, bitmap, "avatar_image");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callBack.onSuccess();
                } else callBack.onFailure(String.valueOf(response.code()));
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
