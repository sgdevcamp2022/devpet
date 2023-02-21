package com.example.petmily.viewModel.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.petmily.R;
import com.example.petmily.model.data.auth.local.AuthDatabase;
import com.example.petmily.model.data.chat.list.local.ChatDatabase;
import com.example.petmily.model.data.chat.list.local.ChatListSQL;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.local.RoomDatabase;
import com.example.petmily.model.data.chat.room.local.RoomSQL;
import com.example.petmily.model.data.chat.room.remote.Room;
import com.example.petmily.model.data.chat.room.remote.RoomAPI_Interface;
import com.example.petmily.model.data.profile.remote.API_Interface;
import com.example.petmily.model.data.profile.remote.ChatRoomMake;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.model.data.profile.remote.Success;
import com.example.petmily.view.MainActivity;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatService extends Service{
    String URL = "https://121.187.22.37:5555/api/chat/chat/";
    public static boolean isRunning = false;

    ChatCallback<?> chatCallback;
    NotificationManager Notifi_M;
    NotificationCompat.Builder Notifi;
    private AuthDatabase auth;
    private List<RoomSQL> roomSQLList;
    private Retrofit retrofit;
    private RoomAPI_Interface chatInterface;
    private String token;
    private RoomDatabase roomDB;
    private ChatDatabase listDB;




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
        if(!isRunning)
            unregisterRestartAlarm();

        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        token= sharedPreferences.getString("token", "");
        auth = AuthDatabase.getInstance(getApplicationContext());
        token = auth.authDao().getToken().getAccessToken();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        client.addInterceptor(new CustomInterceptor());

        OkHttpClient httpClient = client.build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        chatInterface = retrofit.create(RoomAPI_Interface.class);
        roomDB = RoomDatabase.getInstance(ChatService.this);
        listDB = ChatDatabase.getInstance(ChatService.this);

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(isRunning) {
            Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            createNotificationChannel();
//        List<Room> result = new ArrayList<>();
//        result.add(new Room("133a8c93-7952-4e7d-8891-dc4758f554eb", "상대닉네임", "내 닉네임", "메시지메시지", "000000"));
//        String type = "TALK";
//        String roomId = result.get(0).getRoomId();
//        String sender = result.get(0).getSenderName();
//        String receiver = result.get(0).getReceiverName();
//        String message = result.get(0).getMessage();
//        String timeLog = result.get(0).getTimelog();
//        Message addMessage = new Message(type, roomId, sender, receiver, message, timeLog);
            if (!token.equals("")) {
                Call<List<Room>> chatList = chatInterface.getMessage(token);
                chatList.enqueue(chatCallback);
            }
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        if(isRunning)
            registerRestartAlarm();
        super.onDestroy();
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if(isRunning)
            registerRestartAlarm();
        super.onTaskRemoved(rootIntent);
    }

    private void registerRestartAlarm(){
        Intent intent = new Intent(ChatService.this,AlarmReceiver.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(ChatService.this,0,intent,PendingIntent.FLAG_MUTABLE);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 5*1000;

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstTime, sender);
    }

    public void unregisterRestartAlarm(){
        Intent intent = new Intent(ChatService.this,UndeadService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(ChatService.this,0,intent,PendingIntent.FLAG_MUTABLE);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);

    }
    public void createNotificationChannel()
    {
        if(android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("Chat_Channel"
                    ,"Test Notification",Notifi_M.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            Notifi_M.createNotificationChannel(notificationChannel);
        }
    }

    public class ChatCallback<T> implements retrofit2.Callback<List<Room>> {

        final int SUCCESS               = 200;

        final int INVALID_PARAMETER     = 400;
        final int NEED_LOGIN            = 401;
        final int UNAUTHORIZED          = 403;
        final int NOT_FOUND             = 404;

        final int INTERNAL_SERVER_ERROR = 500;

        Context context;

        public ChatCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(Call<List<Room>> call, retrofit2.Response<List<Room>> response) {
            int responseCode = response.code();//네트워크 탐지할 때 사용 코드
            ResponseBody errorBody = response.errorBody();
            if(errorBody != null)
            {
                try {
                    Log.e("채팅 푸시 통신 확인 : ", errorBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                List<Room> result = (List<Room>) response.body();
                Log.e("채팅 푸시 통신 확인 : ",  "size = "+result.size());
            }

            if(responseCode == SUCCESS)
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
                    Message addMessage = new Message(type, roomId, sender, receiver, message, timeLog);

                    RoomSQL roomSQL = roomDB.chatRoomDao().getMessage(roomId);
                    List<Message> messageList;
                    if(roomSQL != null)
                    {
                        messageList = roomSQL.getMessages();
                        if(!messageList.get(messageList.size()-1).equals(message))
                        {
                            messageList.add(addMessage);
                            roomSQL.setMessages(messageList);
                            roomDB.chatRoomDao().updateMessage(roomSQL);
                            ChatListSQL chatListSQL = listDB.chatListDao().getRoomId(roomId);
                            ChatListSQL temp = new ChatListSQL(roomId, timeLog, sender, chatListSQL.getProfileImage(), sender,
                                    chatListSQL.getCount()+1, message, chatListSQL.getCount()+1+"");
                            listDB.chatListDao().updateMessage(temp);


                            Intent intent = new Intent(ChatService.this, MainActivity.class);
                            intent.setData(Uri.parse(roomId));
                            PendingIntent pendingIntent = PendingIntent.getActivity(ChatService.this, 0, intent,PendingIntent.FLAG_MUTABLE);

                            Notifi = new NotificationCompat.Builder(getApplicationContext(), "Chat_Channel")
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle(sender)
                                    .setContentText(message)
                                    .setPriority(chatListSQL.getCount()+1)
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent);

                            Notifi_M.notify(1, Notifi.build());
                        }
                    }
                    else
                    {
                        roomSQL = new RoomSQL(roomId, sender, receiver, null, timeLog);
                        messageList = new ArrayList<Message>();
                        messageList.add(addMessage);
                        roomSQL.setMessages(messageList);
                        List<RoomSQL> roomSQLList = new ArrayList<RoomSQL>();
                        roomSQLList.add(roomSQL);
                        roomDB.chatRoomDao().insertMessage(roomSQLList);

                        ChatListSQL chatListSQL = new ChatListSQL(roomId, timeLog, sender,
                                null, sender, 1, message, "1");
                        List<ChatListSQL> list = new ArrayList<ChatListSQL>();
                        list.add(chatListSQL);
                        listDB.chatListDao().insertMessage(list);

                        Intent intent = new Intent(ChatService.this, MainActivity.class);
                        intent.setData(Uri.parse(roomId));
                        PendingIntent pendingIntent = PendingIntent.getActivity(ChatService.this, 0, intent,PendingIntent.FLAG_MUTABLE);

                        Notifi = new NotificationCompat.Builder(getApplicationContext(), "Chat_Channel")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle(sender)
                                .setContentText(message)
                                .setPriority(chatListSQL.getCount())
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent);

                        Notifi_M.notify(1, Notifi.build());
                    }
                }
            }
            else if(responseCode == 401)//토큰 만료
            {
                AuthenticationViewModel authenticationViewModel = new ViewModelProvider((ViewModelStoreOwner) ChatService.this).get(AuthenticationViewModel.class);
                authenticationViewModel.accessTokenCheck();
            }
        }
        @Override
        public void onFailure(Call<List<Room>> call, Throwable t) {
            Log.e("서비스 연결 실패 : ", t.getMessage());
            t.printStackTrace();
        }
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

    public class CustomInterceptor implements okhttp3.Interceptor, HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            android.util.Log.e("MyGitHubData :", message + "");
        }


        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", token)
                    .build();

            return chain.proceed(request);
        }
    }







}


