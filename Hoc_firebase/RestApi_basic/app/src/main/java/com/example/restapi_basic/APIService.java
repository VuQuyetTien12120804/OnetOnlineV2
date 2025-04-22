package com.example.restapi_basic;

import retrofit2.Call;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    String DOMAIN = "http://192.168.1.95:3000";

    @GET("/api/list")
    Call<List<CarModel>> getCars();

    @POST("/api/add_xe")
    Call<List<CarModel>> addCar(@Body CarModel xe);

    @PUT("/api/update_xe")
    Call<List<CarModel>> updateCar(@Body CarModel xe);

    @DELETE("/api/xoa/{id}")
    Call<List<CarModel>> deleteCar(@Path("id") String id);


}
