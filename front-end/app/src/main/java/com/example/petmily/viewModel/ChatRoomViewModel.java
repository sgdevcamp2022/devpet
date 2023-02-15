package com.example.petmily.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.petmily.model.data.chat.room.Message;

import com.example.petmily.model.data.chat.room.local.RoomDatabase;
import com.example.petmily.model.data.chat.room.local.RoomSQL;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatRoomViewModel extends AndroidViewModel {

    final private String URL = "10.0.2.2:4444";

    private StompClient stompClient;
    boolean isUnexpectedClosed;
    private String TAG = "테스트 :\t";
    private String roomId;
    private RoomDatabase db;
    // private List<ChatRoomSQL> chatList;



    private MutableLiveData<String> senderNickname;
    private MutableLiveData<String> receiverNickname;


    private MutableLiveData<List<Message>> chatMessage;
    public MutableLiveData<List<Message>> getChatMessage() {
        if (chatMessage == null) {
            chatMessage = new MutableLiveData<List<Message>>();
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
    List<Message> source;


    /*
    private MutableLiveData<String> senderName; //email
    private MutableLiveData<String> receiverName;

    private MutableLiveData<String> timelog;
    private MutableLiveData<String> usercount;

    private MutableLiveData<String> profileUrl; //senderName을 통해 프로필
    private String myName;
    private String yourName;
    private String topicId;

     */

    public ChatRoomViewModel(@NonNull Application application) {
        super(application);




        //"2023-01-31T20:50:59.759706"




    }

    public void initChatRoom(String url, String senderNickname, String receiverNickname)// String profileUrl, ChatRoomSQL chatRoomSQL
    {

        source = new ArrayList<Message>();
        chatMessage.setValue(source);
        //db.chatRoomDao().getMessage();
        initStomp(url);
        topicMessage();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC)
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("token", "userId")
                                .build();

                        return chain.proceed(newRequest);
                    }
                }).build();





        /*

        String URL = "http://10.0.2.2:4444/chat/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        API_Interface chatInterface = retrofit.create(API_Interface.class);

        Call<List<String>> testCallback = chatInterface.getMessage();
        testCallback.enqueue(new retrofit2.Callback<List<String>>(){
            //"133a8c93-7952-4e7d-8891-dc4758f554eb"
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {

                Log.e("오류 내용 ", response.message().toString());
                List<String> result = response.body();
                Log.e("get 통신 테스트", result.toString());

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("통신 테스트 실패", t.getMessage());
            }
        });


         */
        /*

        Call<List<Room>> testCallback = chatInterface.getMessage();
        testCallback.enqueue(new retrofit2.Callback<List<Room>>(){
            //"133a8c93-7952-4e7d-8891-dc4758f554eb"
            @Override
            public void onResponse(Call<List<Room>> call, retrofit2.Response<List<Room>> response) {

                Log.e("get 통신 테스트 ", response.message().toString());
                List<Room> result = response.body();
                for(int i = 0 ; i < result.size(); i++)
                {
                    Log.e("get 통신 테스트", result.get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e("통신 테스트 실패", t.getMessage());
            }
        });

         */

        //this.senderNickname.setValue(senderNickname);
        //this.receiverNickname.setValue(receiverNickname);
        //senderName.setValue(senderNickname);
        //receiverName.setValue(receiverNickname); //사용 케이스는 없지만 일단 설정
        //this.profileUrl.setValue(profileUrl);

    }




    public void sendMessage(String text, String myName, String yourName)
    {
        Gson parser=new Gson();
        Message message = new Message("TALK", roomId, myName, yourName, text, "0000");
        stompClient.send("/pub/chat/message", parser.toJson(message)).subscribe();
    }


    @SuppressLint("CheckResult")
    public void topicMessage()
    {
        stompClient.topic("/sub/chat/room/"+roomId).subscribe(topicMessage -> {
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
            source.add(payLoad);
            chatMessage.postValue(source);

        });
    }

    @SuppressLint("CheckResult")
    public void initStomp(String url){
        //stompClient= Stomp.over(Stomp.ConnectionProvider.JWS, "ws://121.187.22.37:8080/ws-stomp/websocket"); // /websocket 꼭 붙이기
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
                        initStomp(url);
                        isUnexpectedClosed=false;
                    }
                    break;
            }

        });
        stompClient.connect();
        roomId = url;
    }
    public void stompClose()
    {
        stompClient.disconnect();
    }






}
