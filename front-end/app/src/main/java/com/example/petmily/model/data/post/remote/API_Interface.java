package com.example.petmily.model.data.post.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Interface {

    @POST
    Call<?> actionResult(@Body Action action);



    @GET("feeds")
    Call<List<Post>> getPost(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("distance") Integer distance,
            @Query("word") String word,
            @Query("start") Integer start,
            @Query("size") Integer size,
            @Query("category") Integer category
    );
    @GET("feeds/marker")
    Call<List<Post>> getMarker(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("distance") Integer distance,
            @Query("word") String word,
            @Query("start") Integer start,
            @Query("size") Integer size,
            @Query("category") Integer category
    );
    @GET("feads/gallery")
    Call<List<Post>> getGallery(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("distance") Integer distance,
            @Query("word") String word,
            @Query("start") Integer start,
            @Query("size") Integer size,
            @Query("category") Integer category
    );

    @GET("feads/myfeads")
    Call<List<Post>> getMyfeads(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("distance") Integer distance,
            @Query("word") String word,
            @Query("start") Integer start,
            @Query("size") Integer size,
            @Query("category") Integer category
    );

    @GET("feads/ref")
    Call<List<Post>> getMy(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("distance") Integer distance,
            @Query("word") String word,
            @Query("start") Integer start,
            @Query("size") Integer size,
            @Query("category") Integer category
    );
}
