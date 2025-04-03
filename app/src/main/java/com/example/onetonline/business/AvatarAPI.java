package com.example.onetonline.business;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AvatarAPI {
    @Multipart
    @POST("avatar")
    Call<Void> uploadAvatar(@Query("id") String id, @Part MultipartBody.Part avatar);

    @GET("avatar/{id}")
    Call<ResponseBody> getAvatar(@Path("id") String id);

    @DELETE("avatar")
    Call<Void> deleteAvatar(@Query("id") String id);
}
