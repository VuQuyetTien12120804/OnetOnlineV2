package com.example.onetonline.data;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AvatarAPI {
    @Multipart
    @POST("avatar/{userName}")
    Call<Void> uploadAvatar(
            @Path("userName") String userName,
            @Part MultipartBody.Part file
    );

    @GET("avatar/{userName}")
    Call<ResponseBody> getAvatar(
            @Path("userName") String userName
    );

    @DELETE("avatar/{userName}")
    Call<Void> deleteAvatar(
            @Path("userName") String userName
    );
}
