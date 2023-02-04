package com.example.petmily.viewModel;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.petmily.R;
import com.example.petmily.model.Access_Token;

import com.example.petmily.model.TempInterface;
import com.example.petmily.model.TestBody;
import com.example.petmily.model.TestChatRoom;
import com.example.petmily.model.TestInterface;
import com.example.petmily.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class ChatService extends Service{
    NotificationManager Notifi_M;
    ChatServiceThread thread;
    NotificationCompat.Builder Notifi;
    //List<ChatRoom_SQL> list;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


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


            Notifi_M.notify(0, Notifi.build());

            //GET 요청을 보내는 로직으로 변경
            /*
            @GET
            localhost:4444/chat/message
            ChatRoomSQL에 저장

             */

            //토스트 띄우기

           // Log.e("서비스 실행" , "실행 중");


            /*
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "ChatRoom_SQL").build();

            List<ChatRoom_SQL> list = db.chatRoomSQL_dao().getAll();

            db.chatRoomSQL_dao().insert(list.get(0));

             */



            /*

            Access_Token accessToken = new Access_Token(token);
            access_tokenCall = logininterface.accessToken(accessToken);


            access_tokenCall.enqueue(new retrofit2.Callback<TempInterface>(){
                @Override
                public void onResponse(Call<TempInterface> call, Response<TempInterface> response) {
                    Access_Token result =(Access_Token) response.body();


                }

                @Override
                public void onFailure(Call<TempInterface> call, Throwable t) {
                    Log.e("결과 테스트 : ", "실패");
                }

            });

             */



        }
    };


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


}