package com.example.petmily.model.data.make.remote;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API_Interface {


    @POST("feeds")
    Call<SuccessMessage> createPost(@Body Post post);

}
