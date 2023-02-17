package com.example.petmily.model.data.chat.room.remote;

import com.example.petmily.model.data.chat.room.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RoomAPI_Interface {

    //@Headers({"token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcl9uYW1lIjoidXNlcjIiLCJpYXQiOjE1MTYyMzkwMjJ9.5QkfscdoCw-tRVGvFzko4WlgJ8fORVh3nFq68LMFZ3Q"})
    @GET("messages")
    Call<List<Room>> getMessage(@Header("token") String token);





}
