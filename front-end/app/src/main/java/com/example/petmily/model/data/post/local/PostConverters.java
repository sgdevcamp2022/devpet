package com.example.petmily.model.data.post.local;

<<<<<<< HEAD

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

=======
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.example.petmily.model.data.chat.room.Message;
>>>>>>> ec9fdf7e880a8cb7a530320265355487b63419e6
import com.example.petmily.model.data.post.remote.Post;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ProvidedTypeConverter
<<<<<<< HEAD
public class PostConverters {
=======
public class Converters {
>>>>>>> ec9fdf7e880a8cb7a530320265355487b63419e6
    @TypeConverter
    public static List<Post> JsonToPost(String value) {
        if(value == null)
        {
            return null;
        }
        else
        {
<<<<<<< HEAD
=======
            Gson gson = new Gson();
>>>>>>> ec9fdf7e880a8cb7a530320265355487b63419e6

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
<<<<<<< HEAD
=======

>>>>>>> ec9fdf7e880a8cb7a530320265355487b63419e6
            return gson.toJson(post);
        }

    }
}