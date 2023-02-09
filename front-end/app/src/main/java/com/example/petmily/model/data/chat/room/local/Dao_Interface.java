package com.example.petmily.model.data.chat.room.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.petmily.model.data.chat.room.Message;

import java.util.List;

@Dao
public interface Dao_Interface {


    //채팅방 입장시 채팅 내역 가져오기
    @Query("SELECT * FROM RoomSQL")
    List<RoomSQL> getMessage();



    //메시지 저장
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMessage(List<RoomSQL> roomSQLList);




}
