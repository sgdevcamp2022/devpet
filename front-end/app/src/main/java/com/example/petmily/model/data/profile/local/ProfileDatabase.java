package com.example.petmily.model.data.profile.local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

import com.example.petmily.model.data.post.local.Converters;
import com.example.petmily.model.data.post.local.PostDao_Interface;
import com.example.petmily.model.data.post.local.PostSQL;

@Database(entities = {ProfileSQL.class},version = 2)
public abstract class ProfileDatabase extends androidx.room.RoomDatabase
{
    private static ProfileDatabase database;


    private static String DATABASE_NAME = "ProfileSQL";
    public synchronized static ProfileDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), ProfileDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()

                    .build();
        }
        return database;
    }
    public abstract ProfileDao_Interface profileDao();
}
