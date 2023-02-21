package com.example.petmily.model.data.chat.room.local;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.example.petmily.model.data.chat.room.Message;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ProvidedTypeConverter
public class RoomConverters {
    @TypeConverter
    public static List<Message> JsonToMessage(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {
            Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
            return new Gson().fromJson(value, listType);
        }


    }

    @TypeConverter
    public static String MessageToJson(List<Message> message) {
        if(message == null)
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();
            return gson.toJson(message);
        }

    }
}