package com.example.petmily.model.data.post.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

@Database(entities = {CommentSQL.class},version = 2)
@TypeConverters({Converters.class})
public abstract class CommentDatabase extends androidx.room.RoomDatabase
{
    private static CommentDatabase database;


    private static String DATABASE_NAME = "CommentDB";
    public synchronized static CommentDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), CommentDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()

                    .build();
        }
        return database;
    }
    public abstract CommentDao_Interface commentDao();
}