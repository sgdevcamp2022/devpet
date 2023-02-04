package com.example.petmily.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChatInterface {

    @GET("{username}")
    Call<Chat> getChatlist(@Path("username") String username);


}
