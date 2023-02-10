package com.example.petmily.model.data.chat.room.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;


@Database(entities = {RoomSQL.class},version = 2)
@TypeConverters({Converters.class})
public abstract class RoomDatabase extends androidx.room.RoomDatabase
{
    private static RoomDatabase database;


    private static String DATABASE_NAME = "RoomDB";
    public synchronized static RoomDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract Dao_Interface chatRoomDao();

}

