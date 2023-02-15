package com.example.petmily.view;




import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityChatRoomBinding;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.viewModel.ChatRoomViewModel;
import com.example.petmily.viewModel.ChatViewModel;

import java.util.List;

public class Activity_Chat_Room extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private Context context;
    private ChatViewModel chatViewModel;
    private RecyclerView messageList;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_room);
        binding.setChatRoom(this);
        context = this;

        init();
    }
    public void init()
    {
        roomId = "";
        roomId = getIntent().getStringExtra("roomId");

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.init();
        chatViewModel.initChatRoom(roomId);

        messageList = binding.chatlist;
        messageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        EditText editText = binding.content;
        editText.setText("");

        Button send = binding.send;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatViewModel.sendMessage(editText.getText().toString());
                editText.setText("");
            }
        });
        initObserver();
    }

    public void initObserver() {
        /*
        final Observer<List<Message>> chatMessageObserver = new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> chatMessage) {
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

         */

        final Observer<List<Message>> messageListObserver = new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> chatMessage) {
                Adapter_Chat_Room newAdapter = new Adapter_Chat_Room(chatMessage);
                messageList.setAdapter(newAdapter);
            }
        };
        chatViewModel.getMessageList().observe(this, messageListObserver);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        chatViewModel.stompClose();
    }

}
