package com.example.petmily.model.data.post.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;


@Database(entities = {PostSQL.class},version = 2)
@TypeConverters({PostConverters.class})
public abstract class PostDatabase extends androidx.room.RoomDatabase
{
    private static PostDatabase database;


    private static String DATABASE_NAME = "PostDB";
    public synchronized static PostDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), PostDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract PostDao_Interface postDao();

}
