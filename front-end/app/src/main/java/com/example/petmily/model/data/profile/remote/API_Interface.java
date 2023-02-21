package com.example.petmily.model.data.profile.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

    @POST("profile")
    Call<Success> saveProfile(@Body Profile profile);

    @GET("profile/my-profile")
    Call<Profile> getMyProfile();

    @GET("profile/{id}")
    Call<Profile> getProfile(@Path("id")String id);

    @POST("profile/room")
    Call<ChatRoomMake> createRoom(@Body List<String> userId);
}
