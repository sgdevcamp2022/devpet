package com.example.petmily.model.data.profile.local;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface ProfileDao_Interface {

    @Query("SELECT * FROM ProfileSQL")
    ProfileSQL getProfile();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProfile(ProfileSQL profileSQL);

}
