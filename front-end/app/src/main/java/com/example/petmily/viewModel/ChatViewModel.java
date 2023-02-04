package com.example.petmily.viewModel;

import static ua.naiksoftware.stomp.dto.LifecycleEvent.Type.OPENED;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.petmily.R;
import com.example.petmily.model.Chat;
import com.example.petmily.model.ChatRoom;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatViewModel extends AndroidViewModel{


    private StompClient stompClient;
    private List<StompHeader> headerList;
    boolean isUnexpectedClosed;
    private String TAG = "테스트 :\t";

    public ChatViewModel(@NonNull Application application) {
        super(application);

        initStomp();



        /*

      AppDatabase db = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, "ChatRoom_SQL").build();

        List<ChatRoom_SQL> list = db.chatRoomSQL_dao().getAll();

         */
       // ChatRoom chatRoom = new ChatRoom("sad", "");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

            }
        });



        /*
        stompClient.topic("subscribe할 주소/make").subscribe(topicMessage -> {
            JSONParser parser=new JSONParser();
            Object obj=parser.parse(topicMessage.getPayload());
            ojb.setText

        });

         */




        Gson gson = new Gson();
        Chat chat=new Chat(R.drawable.ic_launcher_background, "이름", "테스트");//이 클래스에서 필요한 데이터 형식
        stompClient.send("/sub/chat/room/message", gson.toJson(chat)).subscribe();//메시지 전송
    }



    public void sendMessage(String text)
    {
        Gson gson = new Gson();
        Chat chat=new Chat(R.drawable.ic_launcher_background, "이름", text);//이 클래스에서 필요한 데이터 형식
        stompClient.send("/sub/chat/room/message", gson.toJson(chat)).subscribe();//메시지 전송

    }

    /*
                  roomId: '',
                roomName: '',
                message: '',
                messages: [],
                token: '',
                userCount: 0
     */

    public void topicMessage()
    {

    }

    @SuppressLint("CheckResult")
    public void initStomp(){
        stompClient= Stomp.over(Stomp.ConnectionProvider.JWS, "wss://53210263-048a-4162-a41f-bc759625d1a6.mock.pstmn.io/sub/chat/room/websocket"); // /websocket 꼭 붙이기


        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "연결완료");
                    break;
                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());

                    if(lifecycleEvent.getException().getMessage().contains("EOF")){
                        isUnexpectedClosed=true;
                    }
                    break;
                case CLOSED:
                    Log.d(TAG, "연결 종료");
                    if(isUnexpectedClosed){
                        /**
                         * EOF Error
                         */
                        initStomp();
                        isUnexpectedClosed=false;
                    }
                    break;
            }
        });

        headerList=new ArrayList<>();
        //headerList.add(new StompHeader("userid", "유저아이디"));
        //headerList.add(new StompHeader("roomid", "방 아이디"));
        //headerList.add(new StompHeader("헤더값", "토큰값"));
        stompClient.connect(headerList);
    }


    /*
    @Database(entities = {ChatRoom_SQL.class}, version = 1,exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract ChatRoomSQL_Dao chatRoomSQL_dao();
    }

     */


}
