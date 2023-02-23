package com.example.petmily.model.data.profile.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Interface {

    @POST("profile")
    Call<Success> saveProfile(@Body Profile profile);


    @PUT("profile")
    Call<Success> replaceProfile(@Body Profile profile);

    @GET("profile/my-profile")
    Call<Profile> getMyProfile();

    @GET("profile/{id}")
    Call<Profile> getProfile(@Path("id")String id);

    @GET("profile/{id}/follow")
    Call<SuccessFollow> getFollow(@Path("id")String id,
                            @Query("start") int start,
                            @Query("count") int count);

    @GET("profile/{id}/follower")
    Call<SuccessFollower> getFollower(@Path("id")String id,
                            @Query("start") int start,
                            @Query("count") int count);

    @POST("room")
    Call<ChatRoomMake> createRoom(@Body List<String> userId);





}
