package com.example.petmily;

import android.app.Application;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalApplication extends Application {
    public Retrofit retrofit;
    public String API_URL = "https://121.187.37.22:1367/api/";
    public Gson gson;
    public OkHttpClient client;


    @Override
    public void onCreate()
    {

        gson = new Gson();

        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder().baseUrl(API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();



        super.onCreate();
    }


}
