package com.example.petmily.model.data.auth.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface Dao_Interface {

    @Query("SELECT * FROM TokenSQL")
    TokenSQL getToken();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToken(TokenSQL token);


}
