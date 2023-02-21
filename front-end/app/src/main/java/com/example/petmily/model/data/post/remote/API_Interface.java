package com.example.petmily.model.data.post.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Interface {

    @POST
    Call<?> actionResult(@Body Action action);

    @GET("feads")
    Call<List<Post>> getPost(
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("distance") int distance,
            @Field("word") String word,
            @Field("start") int start,
            @Field("size") int size,
            @Field("category") int category
    );


    /*
    @GET
    Call<>

     */
}
