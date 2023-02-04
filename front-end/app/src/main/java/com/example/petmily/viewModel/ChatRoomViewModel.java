package com.example.petmily.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.petmily.model.ChatMessage;
import com.example.petmily.model.ChatContentSQL;

import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatRoomViewModel extends AndroidViewModel {

    private StompClient stompClient;
    boolean isUnexpectedClosed;
    private String TAG = "테스트 :\t";
    private String URL;
    //private ChatViewModel.AppDatabase db;
    //private List<ChatRoom_SQL> chatList;

    /*
        @SerializedName("roomId")
    public String roomId;

    @SerializedName("senderName")
    public String senderName;

    @SerializedName("receiverName")
    public String receiverName;

    @SerializedName("messages")
    public List<ChatMessage> messages;

    @SerializedName("timelog")
    public String timelog;

     */



    private MutableLiveData<String> senderNickname;
    private MutableLiveData<String> receiverNickname;


    private MutableLiveData<List<ChatMessage>> chatMessage;
    public MutableLiveData<List<ChatMessage>> getChatMessage() {
        if (chatMessage == null) {
            chatMessage = new MutableLiveData<List<ChatMessage>>();
        }
        return chatMessage;
    }

    private MutableLiveData<String> messages;
    public MutableLiveData<String> getMessages() {
        if (messages == null) {
            messages = new MutableLiveData<String>();
        }
        return messages;
    }

    private MutableLiveData<String> senderName; //email
    private MutableLiveData<String> receiverName;

    private MutableLiveData<String> timelog;
    private MutableLiveData<String> usercount;


/*

    public MutableLiveData<String> getSenderName() {
        if (senderName == null) {
            senderName = new MutableLiveData<String>();
        }
        return senderName;
    }
    public MutableLiveData<String> getReceiverName() {
        if (receiverName == null) {
            receiverName = new MutableLiveData<String>();
        }
        return receiverName;
    }

    public MutableLiveData<String> getTimelog() {
        if (timelog == null) {
            timelog = new MutableLiveData<String>();
        }
        return timelog;
    }
    public MutableLiveData<String> getUsercount() {
        if (usercount == null) {
            usercount = new MutableLiveData<String>();
        }
        return usercount;
    }

    public MutableLiveData<String> getSenderNickname() {
        if (senderNickname == null) {
            senderNickname = new MutableLiveData<String>();
        }
        return senderNickname;
    }
    public MutableLiveData<String> getReceiverNickname() {
        if (receiverNickname == null) {
            receiverNickname = new MutableLiveData<String>();
        }
        return receiverNickname;
    }


     */



    List<ChatMessage> source;


    private MutableLiveData<String> profileUrl; //senderName을 통해 프로필
    private String myName;
    private String yourName;
    private String topicId;

    public ChatRoomViewModel(@NonNull Application application) {
        super(application);



        //"2023-01-31T20:50:59.759706"




    }

    public void initChatRoom(String url, String senderNickname, String receiverNickname)// String profileUrl
    {

        source = new ArrayList<ChatMessage>();
        source.add(new ChatMessage("TEXT", "sadsa", "sender", "receiver", "message", "timeLog"));
        source.add(new ChatMessage("TEXT", "sadsa", "sender", "receiver", "message", "timeLog"));
        source.add(new ChatMessage("TEXT", "sadsa", "sender", "receiver", "message", "timeLog"));
        source.add(new ChatMessage("TEXT", "sadsa", "sender", "receiver", "message", "timeLog"));

        chatMessage.setValue(source);

        //this.senderNickname.setValue(senderNickname);
        //this.receiverNickname.setValue(receiverNickname);
        initStomp(url);
        topicMessage();
        //senderName.setValue(senderNickname);
        //receiverName.setValue(receiverNickname); //사용 케이스는 없지만 일단 설정
        //this.profileUrl.setValue(profileUrl);

    }




    public void sendMessage(String text, String myName, String yourName)
    {
        Gson parser=new Gson();
        ChatMessage chatMessage = new ChatMessage("TEXT", URL, myName, yourName, text, "0000");
        stompClient.send("/sub/chat/room/"+URL, parser.toJson(chatMessage)).subscribe();


    }


    @SuppressLint("CheckResult")
    public void topicMessage()
    {


        stompClient.topic("/sub/chat/room/"+URL).subscribe(topicMessage -> {
            topicId = stompClient.getTopicId("/sub/chat/room/"+URL);
            Gson parser=new Gson();
            ChatMessage testMessage = parser.fromJson(topicMessage.getPayload(), ChatMessage.class);


            //ChatMessage payLoad =parser.fromJson(topicMessage.getPayload(), ChatMessage.class);


            if(stompClient.getTopicId("/sub/chat/room/"+URL).equals(topicId))//중복 실행 방지
            {
                source.add(testMessage);
                chatMessage.postValue(source);
            }





            //chatMessage.setValue(temp);


            //messages.setValue(payLoad.getMessage());
            //timelog.setValue(payLoad.getTimeLog());

            //userCount.setValue(chatMessage.getuserCount);

        });


    }

    @SuppressLint("CheckResult")
    public void initStomp(String url){
        stompClient= Stomp.over(Stomp.ConnectionProvider.JWS, "ws://10.0.2.2:4444/ws-stomp/websocket"); // /websocket 꼭 붙이기




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
                        initStomp(url);
                        isUnexpectedClosed=false;
                    }
                    break;
            }

        });





        stompClient.connect();
        URL = url;
    }
    public void stompClose()
    {
        stompClient.disconnect();
    }


    public List<ChatContentSQL> getChatRoomSQL()
    {
        List<ChatContentSQL> chatContentSQL = new ArrayList<ChatContentSQL>();

        return null;
    }

    public void addText()
    {

    }








    //public Chat_SQL


    /* ChatRoomSQL_Dao
    @Dao
public interface ChatRoomSQL_Dao {


    @Query("SELECT * FROM ChatRoom_SQL")
    List<ChatRoom_SQL> getAll();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatRoom_SQL chatRoom_sql);

    @Delete
    void delete(ChatRoom_SQL chatRoom_sql);

    @Update
    void update(ChatRoom_SQL chatRoom_sql);


}
     */

    /*Chat_SQL
        @NonNull
    @PrimaryKey
    String nickname;

    //@ColumnInfo(name = "profile_image")
    String profile_image;

    //@ColumnInfo(name = "chat")
    String chat;

    //@ColumnInfo(name = "count")
    int count;

    //@ColumnInfo(name = "time")
    String time;
     */



    /*ChatRoom_SQL
     @NonNull
    @PrimaryKey
    //@ColumnInfo(name = "roomId")
    public String roomId;

    //@ColumnInfo(name = "senderName")
    public String senderName;

    //@ColumnInfo(name = "receiverName")
    public String receiverName;

    @Embedded
    public List<ChatMessage> messages;

    //@ColumnInfo(name = "timelog")
    public String timelog;

     */




}
