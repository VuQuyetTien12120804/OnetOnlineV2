package com.example.retrofit_baisic.api;

import com.example.retrofit_baisic.model.Currentcy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiservice = new Retrofit.Builder().baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);
    //https://api.github.com/users/1

    @GET("api/live")
    Call<Currentcy> convertUsdtoVnd(@Query("access_key") String access_key,
                                    @Query("currencies") String currencies,
                                    @Query("source") String source,
                                    @Query("fomat") int fomat);

    @GET("https://api.github.com/users/1")
    Call<Currentcy> convertUsdtoVnd1();
}
