package com.example.petmily.viewModel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import com.example.petmily.R;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.remote.API_Interface;
import com.example.petmily.view.MainActivity;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatService extends Service{
    NotificationManager Notifi_M;
    ChatServiceThread thread;
    NotificationCompat.Builder Notifi;
    //List<ChatRoom_SQL> list;

    OkHttpClient.Builder httpClient;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        httpClient = new OkHttpClient.Builder();


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcl9uYW1lIjoidXNlcjIiLCJpYXQiOjE1MTYyMzkwMjJ9.5QkfscdoCw-tRVGvFzko4WlgJ8fORVh3nFq68LMFZ3Q")
                        .build();

                return chain.proceed(request);
            }
        });



        test test = new test();


        httpClient.addInterceptor(test);


        //httpClient.addNetworkInterceptor(test);
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ChatServiceThread(handler);
        thread.start();





        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.

    }

    class myServiceHandler extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(ChatService.this, MainActivity.class);
            //PendingIntent pendingIntent = PendingIntent.getActivity(ChatService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);


            Notifi = new NotificationCompat.Builder(getApplicationContext(), "채널 아이디")//채널
                    .setSmallIcon(R.drawable.corner)
                    .setContentTitle("asdsad")
                    .setContentText("asdsadsad")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            //Notifi_M.notify(0, Notifi.build());



            /*

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                            .setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request mRequest = chain.request();
                            Request newRequest  = mRequest.newBuilder()
                                    .addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcl9uYW1lIjoidXNlcjIiLCJpYXQiOjE1MTYyMzkwMjJ9.5QkfscdoCw-tRVGvFzko4WlgJ8fORVh3nFq68LMFZ3Q")
                                    .build();
                            Response mResponse = chain.proceed(newRequest);

                            return mResponse;
                        }
                    }).build();





             */


            OkHttpClient client = httpClient.build();



            //String URL = "http://121.187.22.37:8080/chat/";
            String URL = "http://10.0.2.2:4444/chat/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(client)
                    .build();

            API_Interface chatInterface = retrofit.create(API_Interface.class);

            Call<List<String>> testCallback = chatInterface.getMessage("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcl9uYW1lIjoidXNlcjIiLCJpYXQiOjE1MTYyMzkwMjJ9.5QkfscdoCw-tRVGvFzko4WlgJ8fORVh3nFq68LMFZ3Q");
            testCallback.enqueue(new retrofit2.Callback<List<String>>(){
                //"133a8c93-7952-4e7d-8891-dc4758f554eb"
                @Override
                public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {

                    List<Message> result = null;

                    if(response.body()!=null) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
                        result = new Gson().fromJson(response.body().toString(), listType);
                        for(int i = 0 ; i <result.size(); i++)
                        {

                            Log.e("get 통신 테스트", result.get(i).toString());
                        }
                    }
                    else
                    {
                        Log.e("null", "");
                    }

                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("통신 테스트 실패", t.getMessage());
                    t.printStackTrace();
                }
            });








        }
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("아이디", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /*
    @Database(entities = {ChatRoom_SQL.class}, version = 1,exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract ChatRoomSQL_Dao chatRoomSQL_dao();
    }

     */


    public class test implements Interceptor
    {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request response = null;

            Request original = chain.request();

            Request.Builder request = original.newBuilder();
            request.addHeader("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcl9uYW1lIjoidXNlcjIiLCJpYXQiOjE1MTYyMzkwMjJ9.5QkfscdoCw-tRVGvFzko4WlgJ8fORVh3nFq68LMFZ3Q");

            response = request.build();

            Log.e("여기내용 오류 ", chain.proceed(response).headers().toString());


            return chain.proceed(response);
        }

    }
}
