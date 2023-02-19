package com.example.petmily.viewModel.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.petmily.model.data.auth.local.AuthDatabase;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.local.RoomDatabase;
import com.example.petmily.model.data.chat.room.local.RoomSQL;
import com.example.petmily.model.data.chat.room.remote.Room;
import com.example.petmily.model.data.chat.room.remote.RoomAPI_Interface;
import com.example.petmily.view.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatService extends Service{
    String URL = "https://121.187.22.37:5555/api/chat/chat";

    NotificationManager Notifi_M;
    ChatServiceThread thread;
    NotificationCompat.Builder Notifi;
    private AuthDatabase auth;
    private List<RoomSQL> roomSQLList;
    private Retrofit retrofit;
    private RoomAPI_Interface chatInterface;
    private String token;
    private RoomDatabase db;


    int count = 0;

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
        token = auth.authDao().getToken().getAccessToken();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatInterface = retrofit.create(RoomAPI_Interface.class);

        db = RoomDatabase.getInstance(ChatService.this);

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

        if(count <= 100)
        {
            registerRestartAlarm();
        }


    }

    class myServiceHandler extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(ChatService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(ChatService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            count++;
            if(count > 100)
            {
                onDestroy();
            }

            Notifi = new NotificationCompat.Builder(getApplicationContext(), "채널 아이디")//채널
                    .setSmallIcon(R.drawable.corner)
                    .setContentTitle("제목")
                    .setContentText("메시지 테스트")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);


            Notifi_M.notify(0, Notifi.build());

            if(!token.equals(""))
            {
                Call<List<Room>> chatList = chatInterface.getMessage(token);
                chatList.enqueue(new retrofit2.Callback<List<Room>>() {
                    @Override
                    public void onResponse(Call<List<Room>> call, retrofit2.Response<List<Room>> response) {
                        if(response.code() == 200)
                        {
                            List<Room> result = response.body();
                            roomSQLList = new ArrayList<>();

                            for(int i = 0; i < result.size(); i++)
                            {
                                String type = "TALK";
                                String roomId = result.get(i).getRoomId();
                                String sender = result.get(i).getSenderName();
                                String receiver = result.get(i).getReceiverName();
                                String message = result.get(i).getMessage();
                                String timeLog = result.get(i).getTimelog();
                                Message addMessage = new Message("TALK", roomId, sender, receiver, message, timeLog);

                                RoomSQL roomSQL = db.chatRoomDao().getMessage(roomId);
                                List<Message> messageList = roomSQL.getMessages();
                                messageList.add(addMessage);
                                roomSQL.setMessages(messageList);

                                db.chatRoomDao().updateMessage(roomSQL);
                            }

//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
//                            result = new Gson().fromJson(response.body().toString(), listType);
                        }
                        else if(response.code() == 401)//토큰 만료
                        {

                        }
                    }
                    @Override
                    public void onFailure(Call<List<Room>> call, Throwable t) {
                        Log.e("서비스 연결 실패 : ", "");
                                t.printStackTrace();

                    }
                });
            }
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


