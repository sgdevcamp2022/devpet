package com.example.petmily.model.data.post.local;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.example.petmily.model.data.post.Entity.HashTags;
import com.example.petmily.model.data.post.Entity.Location;

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
    public static List<Integer> JsonToInteger(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {
            Type listType = new TypeToken<List<Integer>>(){}.getType();
            return new Gson().fromJson(value, listType);
        }
    }

    @TypeConverter
    public static String IntegerToJson(List<Integer> post) {
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
    public static HashTags JsonToHashTags(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {
            Type listType = new TypeToken<HashTags>(){}.getType();
            return new Gson().fromJson(value, listType);
        }
    }

    @TypeConverter
    public static String HashTagsToJson(HashTags post) {
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