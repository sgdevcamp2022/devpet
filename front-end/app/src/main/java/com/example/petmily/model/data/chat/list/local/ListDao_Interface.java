package com.example.petmily.model.data.chat.list.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.petmily.model.data.chat.list.ChatList;

import java.util.List;

@Dao
public interface ListDao_Interface {


    //채팅방 입장시 채팅 내역 가져오기
    @Query("SELECT * FROM ChatListSQL")
    List<ChatListSQL> getChatList();



    //메시지 저장
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(List<ChatListSQL> chatSQLList);

    @Query("SELECT * FROM ChatListSQL WHERE roodId = :id")
    ChatListSQL getRoomId(String id);

}
