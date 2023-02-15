package com.example.petmily.model.data.chat.room.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.petmily.model.data.chat.room.Message;

import java.util.List;

@Dao
public interface RoomDao_Interface {


    //채팅방 입장시 채팅 내역 가져오기
    @Query("SELECT * FROM RoomSQL WHERE roomId = :roomId")
    RoomSQL getMessage(String roomId);



    //메시지 저장
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMessage(List<RoomSQL> roomSQLList);

    @Update
    void updateMessage(RoomSQL roomSQLList);




}
