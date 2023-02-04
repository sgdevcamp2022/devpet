package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityChatBinding;
import com.example.petmily.databinding.ActivityChatRoomBinding;

import com.example.petmily.model.ChatMessage;
import com.example.petmily.model.ChatRoom;
import com.example.petmily.model.Place;
import com.example.petmily.model.TestChatRoom;
import com.example.petmily.model.TestInterface;
import com.example.petmily.viewModel.ChatRoomViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Chat_Room extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private Context context;
    private ChatRoomViewModel chatRoomViewModel;
    private RecyclerView chatlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_room);
        binding.setChatRoom(this);
        context = this;
        String roomId = "";
        String senderNickname = "testname";
        String receiverNickname = "ttestname";










        roomId = getIntent().getStringExtra("roomId");


        chatRoomViewModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        initObserver();
        chatRoomViewModel.initChatRoom(roomId,senderNickname, receiverNickname);
        //chatRoomViewModel.getChatMessageSQL();



        chatlist = binding.chatlist;
        chatlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        Log.e("룸 내에서 Id 확인 : ", roomId);




        EditText editText = binding.content;
        editText.setText("");

        Button send = binding.send;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatRoomViewModel.sendMessage(editText.getText().toString(), "유저1", "유저2");

            }
        });



    }
    public void initObserver() {
        final Observer<List<ChatMessage>> chatMessageObserver = new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(@Nullable final List<ChatMessage> chatMessage) {
                Adapter_Chat_Room newAdapter = new Adapter_Chat_Room(chatMessage);
                chatlist.setAdapter(newAdapter);
            }
        };
        chatRoomViewModel.getChatMessage().observe(this, chatMessageObserver);

        final Observer<String> messagesObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String messages) {


            }
        };
        chatRoomViewModel.getMessages().observe(this, messagesObserver);

    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        chatRoomViewModel.stompClose();

    }

}
