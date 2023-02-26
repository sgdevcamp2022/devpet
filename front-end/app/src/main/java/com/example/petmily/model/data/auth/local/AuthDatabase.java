package com.example.petmily.model.data.auth.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;



@Database(entities = {TokenSQL.class},version = 3)
public abstract class AuthDatabase extends androidx.room.RoomDatabase
{
    private static AuthDatabase database;


    private static String DATABASE_NAME = "AuthDB";
    public synchronized static AuthDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AuthDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract Dao_Interface authDao();

}
