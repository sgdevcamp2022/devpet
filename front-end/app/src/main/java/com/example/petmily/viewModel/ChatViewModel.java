package com.example.petmily.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.petmily.model.data.chat.list.ChatList;
import com.example.petmily.model.data.chat.list.local.ChatDatabase;
import com.example.petmily.model.data.chat.list.local.ChatListSQL;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.local.RoomDatabase;
import com.example.petmily.model.data.chat.room.local.RoomSQL;
import com.google.gson.Gson;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatViewModel extends AndroidViewModel{

    final private String URL = "https://121.187.37.22:5555/api/chat/";
    //final private String URL = "10.0.2.2:4444";

    boolean isUnexpectedClosed;
    private String TAG = "테스트 :\t";

    private RoomSQL roomSQL;
    private ChatDatabase listDB;
    private RoomDatabase roomDB;

    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;

    private List<Message> messages;

    private StompClient stompClient;

    private MutableLiveData<List<Message>> messageList;
    public MutableLiveData<List<Message>> getMessageList() {
        if (messageList == null) {
            messageList = new MutableLiveData<List<Message>>();
        }
        return messageList;
    }

    private MutableLiveData<List<ChatList>> chatList;
    public MutableLiveData<List<ChatList>> getChatList() {
        if (chatList == null) {
            chatList = new MutableLiveData<List<ChatList>>();
        }
        return chatList;
    }

    public ChatViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public void init()
    {
        Gson gson = new Gson();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        listDB = ChatDatabase.getInstance(context);
        roomDB = RoomDatabase.getInstance(context);
    }
    public void initChatRoom(String roomId)
    {
        roomSQL = roomDB.chatRoomDao().getMessage(roomId);
        if(roomSQL != null)
        {
            messageList.setValue(roomSQL.getMessages());
        }

        initStomp();
        topicMessage();
    }



    public void refreshChatList()
    {
        List<ChatListSQL> list = listDB.chatListDao().getChatList();
        List<ChatList> chatLists = new ArrayList<ChatList>();
        for(int i = 0; i < list.size(); i++)
        {
            String roomIdLive = list.get(i).getRoodId();

//            String strDate = list.get(i).getTimeLog();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//            Date currentDay = dateFormat.parse(strDate, new ParsePosition(0));
//            Long currentLong = currentDay.getTime();
//            formatTimeString(currentLong);

            String timeLog = list.get(i).getTimeLog();
            String senderNickname = list.get(i).getSenderNickname();
            String profileImage = list.get(i).getProfileImage();
            String sender = list.get(i).getSender();
            int count = list.get(i).getCount();
            String lastText = list.get(i).getLastText();
            String alarm = count+"";
            ChatList chatList = new ChatList(roomIdLive, timeLog, senderNickname, profileImage, sender, count, lastText, alarm);
            chatLists.add(chatList);
        }
        chatList.setValue(chatLists);
    }

    public void sendMessage(String text)
    {

        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Gson parser=new Gson();
        //TALK IMAGE VIDEO 판단 로직 구현 필요
        Message message = new Message("TALK", roomSQL.getRoomId(), roomSQL.getReceiverName(), roomSQL.getSenderName(), text, sDate2.toString());
        stompClient.send("/pub/chat/message", parser.toJson(message)).subscribe();
    }

    @SuppressLint("CheckResult")
    public void topicMessage()
    {
        stompClient.topic("/sub/chat/room/"+roomSQL.getRoomId()).subscribe(topicMessage -> {
            Gson parser=new Gson();
            Message payLoad = parser.fromJson(topicMessage.getPayload(), Message.class);
            /*
            if(payLoad.getType().equals("ENTER"))
            {

            }
            else if(payLoad.getType().equals("EXIT"))
            {

            }
            else
            {
            }
             */
            messages.add(payLoad);
            roomSQL.getMessages().add(payLoad);

            roomDB.chatRoomDao().updateMessage(roomSQL);
            messageList.setValue(messages);
        });
    }

    @SuppressLint("CheckResult")
    public void initStomp(){
        stompClient= Stomp.over(Stomp.ConnectionProvider.JWS, "ws://"+URL+"/ws-stomp/websocket");
        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.e(TAG, "연결완료");
                    break;
                case ERROR:
                    Log.e(TAG, "에러 발생 : ", lifecycleEvent.getException());
                    if(lifecycleEvent.getException().getMessage().contains("EOF")){
                        isUnexpectedClosed=true;
                    }
                    break;
                case CLOSED:
                    Log.e(TAG, "연결 종료");
                    if(isUnexpectedClosed){
                        initStomp();
                        isUnexpectedClosed=false;
                    }
                    break;
            }
        });
        stompClient.connect();
    }
    public void stompClose()
    {
        stompClient.disconnect();
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

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }


}
