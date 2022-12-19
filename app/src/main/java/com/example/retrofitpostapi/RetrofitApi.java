package com.example.retrofitpostapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RetrofitApi {
    @POST("users")
    Call<DataModel> createPost(@Body DataModel dataModel);

    @PUT("users")

    Call<DataModel> updateData(@Body DataModel dataModel);
}
