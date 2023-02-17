package com.example.petmily.viewModel.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.petmily.R;
import com.example.petmily.model.data.chat.list.local.ChatDatabase;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.remote.RoomAPI_Interface;
import com.example.petmily.view.Activity_Chat_Room;
import com.example.petmily.view.MainActivity;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatService extends Service{
    String URL = "https://121.187.37.22:5555/api/chat";

    NotificationManager Notifi_M;
    ChatServiceThread thread;
    NotificationCompat.Builder Notifi;
    //List<ChatRoom_SQL> list;
    private Retrofit retrofit;
    private RoomAPI_Interface chatInterface;
    private String token;
    private ChatDatabase db;


    private SingleLiveEvent<Boolean> eventLoginExpiration;
    public SingleLiveEvent<Boolean> getEventLoginExpiration() {
        if (eventLoginExpiration == null) {
            eventLoginExpiration = new SingleLiveEvent<Boolean> ();
        }
        return eventLoginExpiration;
    }

    @Override
    public void onCreate()
    {
        unregisterRestartAlarm();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        token= sharedPreferences.getString("token", "");

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatInterface = retrofit.create(RoomAPI_Interface.class);

        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ChatServiceThread(handler);
        thread.start();


        return START_STICKY;
    }
    //서비스가 종료될 때 할 작업
    @Override
    public void onDestroy() {
        super.onDestroy();
//        thread.stopForever();
//        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.

        registerRestartAlarm();

    }

    class myServiceHandler extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(ChatService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(ChatService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);




            Notifi = new NotificationCompat.Builder(getApplicationContext(), "채널 아이디")//채널
                    .setSmallIcon(R.drawable.corner)
                    .setContentTitle("제목")
                    .setContentText("메시지 테스트")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);



            Notifi_M.notify(0, Notifi.build());

            if(!token.equals(""))
            {
                Call<List<String>> chatList = chatInterface.getMessage(token);
                chatList.enqueue(new retrofit2.Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                        List<Message> result = null;


                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
                        result = new Gson().fromJson(response.body().toString(), listType);
                        for(int i = 0 ; i <result.size(); i++)
                        {

                            Log.e("get 통신 테스트", result.get(i).toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });
            }

            Call<List<String>> testCallback = chatInterface.getMessage(token);
            testCallback.enqueue(new retrofit2.Callback<List<String>>(){
                @Override
                public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {

                    if(response.body()!=null) {

                    }
                    else
                    {
                        //Log.e("null", "");
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

    private void registerRestartAlarm(){

        Log.i("000 PersistentService" , "registerRestartAlarm" );
        Intent intent = new Intent(ChatService.this,UndeadService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(ChatService.this,0,intent,0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 1*1000;

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        /**
         * 알람 등록
         */
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime,1*1000,sender);

    }

    /**
     * 알람 매니져에 서비스 해제
     */
    private void unregisterRestartAlarm(){

        Log.i("000 PersistentService" , "unregisterRestartAlarm" );

        Intent intent = new Intent(ChatService.this,UndeadService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(ChatService.this,0,intent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        /**
         * 알람 취소
         */
        alarmManager.cancel(sender);

    }

    public class SingleLiveEvent<T> extends MutableLiveData<T> {

        private static final String TAG = "SingleLiveEvent";

        private final AtomicBoolean mPending = new AtomicBoolean(false);

        @MainThread
        public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

            if (hasActiveObservers()) {
                Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
            }

            // Observe the internal MutableLiveData
            super.observe(owner, new Observer<T>() {
                @Override
                public void onChanged(@Nullable T t) {
                    if (mPending.compareAndSet(true, false)) {
                        observer.onChanged(t);
                    }
                }
            });
        }

        @MainThread
        public void setValue(@Nullable T t) {
            mPending.set(true);
            super.setValue(t);
        }

        /**
         * Used for cases where T is Void, to make calls cleaner.
         */
        @MainThread
        public void call() {
            setValue(null);
        }
    }
}


