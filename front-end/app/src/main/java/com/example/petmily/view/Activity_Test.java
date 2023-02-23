package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.Code;
import com.example.petmily.R;
import com.example.petmily.databinding.ActivityChatRoomBinding;
import com.example.petmily.databinding.ActivityTestBinding;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.viewModel.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

public class Activity_Test extends AppCompatActivity {

    private ActivityTestBinding binding;
    private Context context;
    private ChatViewModel chatViewModel;
    private RecyclerView messageList;

    private String userId;
    @NonNull
    private String roomId;
    List<Message> chatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setChatRoom(this);
        context = this;
        chatMessage = new ArrayList<>();
        init();
    }

    public void init() {
        messageList = binding.chatlist;
        messageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        EditText editText = binding.content;
        editText.setText("");
        int count = 0;
        Button send = binding.send;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message;
                if(count %2 == 1)
                {
                    message = new Message("TEXT", "", "내 이름", "상대 이름",
                            editText.getText().toString(), "시간", Code.ViewType.LEFT_CONTENT);
                }
                else
                {
                    message = new Message("TEXT", "", "내 이름", "상대 이름",
                            editText.getText().toString(), "시간", Code.ViewType.RIGHT_CONTENT);
                }

                chatMessage.add(message);
                Adapter_Chat_Room newAdapter = new Adapter_Chat_Room(chatMessage);
                messageList.setAdapter(newAdapter);

                editText.setText("");
            }
        });



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}