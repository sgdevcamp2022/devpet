package com.example.petmily.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestInterface {

    @POST("room")
    Call<TestChatRoom> createRoom(@Body List<String> asdsda);

    @GET("room")
    Call<TestChatRoom> test();


}
