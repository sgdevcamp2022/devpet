package com.example.petmily.model.data.post.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Interface {

    @POST
    Call<?> actionResult(@Body Action action);

    @GET
    Call<List<Post>> getPost();


    /*
    @GET
    Call<>

     */
}
