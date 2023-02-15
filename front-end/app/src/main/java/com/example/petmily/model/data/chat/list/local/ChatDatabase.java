package com.example.petmily.model.data.chat.list.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {ChatListSQL.class},version = 2)
public abstract class ChatDatabase extends androidx.room.RoomDatabase
{
    private static ChatDatabase database;


    private static String DATABASE_NAME = "RoomDB";
    public synchronized static ChatDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), ChatDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract ListDao_Interface chatListDao();

}
