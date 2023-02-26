package com.example.petmily.model.data.post.remote;


import com.example.petmily.model.data.post.Entity.AddComment;
import com.example.petmily.model.data.post.Entity.ReplaceComment;
import com.example.petmily.model.data.post.Entity.UserId;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Interface {

    @POST("feed")
    Call<String> actionResult(@Body List<Action> action);


    @GET("feed")
    Call<List<String>> getSearch(@Body UserId userId);

    @GET("feeds/{feedId}/comment")
    Call<Success> getComment(@Path("feedId")int feedId,
                             @Query("start") int start,
                             @Query("count") int count,
                             @Query("rootCommentId") int rootCommentId
    );




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

    @GET("feeds")
    Call<List<Post>> searchPost(
            @Query("word") String word,
            @Query("start") Integer start,
            @Query("size") Integer size
    );

    @GET("feeds/my-feed")
    Call<List<String>> getMyfeed(
            @Query("start") Integer start,
            @Query("size") Integer size
    );
    @GET("feeds/recommend")
    Call<List<Post>> getRecommend(
            @Query("start") Integer start,
            @Query("size") Integer size);

    @GET("feeds/{feedId}/emotion")
    Call<Success> getLike(@Path("feedId") int feedId);

    @DELETE("feeds/{feedId}")
    Call<Success> remove(@Path("feedId") int feedId);

    @POST("feeds/{feedId}/comment")
    Call<Success> addComment(@Path("feedId") int feedId,
                             @Body AddComment addComment);

    @PUT("feeds/{feedId}/comment")
    Call<Success> replaceComment(@Path("feedId") int feedId,
                             @Body ReplaceComment replaceComment);


}
