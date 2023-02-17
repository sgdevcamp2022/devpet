package com.example.petmily.model.data.chat.list.remote;

import com.example.petmily.model.data.profile.remote.ChatRoomMake;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ListAPI_Interface {

    //프로필 서버로 이동할 예정
    @POST("room")
    Call<ChatRoomMake> createRoom(@Body List<String> userId);
}
