package com.example.petmily.model.data.post.local;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.post.remote.Post;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ProvidedTypeConverter
public class Converters {
    @TypeConverter
    public static List<Post> JsonToPost(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Post>>(){}.getType();


            return new Gson().fromJson(value, listType);
        }


    }

    @TypeConverter
    public static String PostToJson(List<Post> post) {
        if(post == null)
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();

            return gson.toJson(post);
        }

    }
}