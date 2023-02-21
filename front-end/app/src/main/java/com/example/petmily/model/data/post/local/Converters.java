package com.example.petmily.model.data.post.local;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;
import com.example.petmily.model.data.post.remote.Post;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ProvidedTypeConverter
public class Converters {
    @TypeConverter
    public static List<String> JsonToString(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {

            Type listType = new TypeToken<ArrayList<String>>(){}.getType();


            return new Gson().fromJson(value, listType);
        }


    }

    @TypeConverter
    public static String StringToJson(List<String> strings) {
        if(strings == null)
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();
            return gson.toJson(strings);
        }

    }

    @TypeConverter
    public static Profile JsonToProfile(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {

            Type listType = new TypeToken<Profile>(){}.getType();


            return new Gson().fromJson(value, listType);
        }


    }

    @TypeConverter
    public static String ProfileToJson(Profile post) {
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

    @TypeConverter
    public static Location JsonToLcation(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {

            Type listType = new TypeToken<Location>(){}.getType();


            return new Gson().fromJson(value, listType);
        }


    }

    @TypeConverter
    public static String LocationToJson(Location post) {
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

    @TypeConverter
    public static List<Comment> JsonToComments(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {

            Type listType = new TypeToken<List<Comment>>(){}.getType();


            return new Gson().fromJson(value, listType);
        }


    }

    @TypeConverter
    public static String CommentsToJson(List<Comment> post) {
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