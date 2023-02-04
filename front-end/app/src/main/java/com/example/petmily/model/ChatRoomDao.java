package com.example.petmily.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatRoomDao {
    @Query("SELECT * FROM ChatContentSQL")
    LiveData<List<ChatContentSQL>> getAll();
}
