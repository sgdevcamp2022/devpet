package com.example.petmily.model.data.profile.remote;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

    @POST
    Call<?> saveProfile(@Body Profile profile);

    @GET("{id}")
    Call<?> getProfile(@Path("id")String id);
}
