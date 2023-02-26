package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentMessageBinding;
import com.example.petmily.model.data.chat.list.ChatList;
import com.example.petmily.model.data.chat.room.remote.RoomAPI_Interface;
import com.example.petmily.viewModel.ChatViewModel;
import com.example.petmily.viewModel.ProfileViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Message extends Fragment {
    private String URL = "http://ec2-54-248-187-237.ap-northeast-1.compute.amazonaws.com:8080/api/chat/";

    private FragmentMessageBinding binding;
    private Context context;

    private ChatViewModel chatViewModel;
    private RecyclerView chat;
    ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_message, container, false);
        View view = binding.getRoot();
        context = container.getContext();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        init();
        return view;
    }

    public void init()
    {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.init();
        chat = binding.messageList;
        chat.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel.createChatRoom(binding.editTextTextPersonName.getText().toString());


            }
        });

        initObserver();
    }


    public void initObserver()
    {
        final Observer<List<ChatList>> chatListObserver  = new Observer<List<ChatList>>() {
            @Override
            public void onChanged(@Nullable final List<ChatList> chatList) {
                Adapter_Message newAdapter = new Adapter_Message(chatList);
                newAdapter.setOnItemClickListener(new Adapter_Message.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position, String roomId) {

                        Intent intent = new Intent(v.getContext(), Activity_Chat_Room.class);
                        intent.putExtra("roomId", roomId);//실제 작동 방식
                        //intent.putExtra("roomId", "133a8c93-7952-4e7d-8891-dc4758f554eb");//테스트용 roomId
                        startActivity(intent);
                    }
                });
                chat.setAdapter(newAdapter);
            }
        };
        chatViewModel.getChatList().observe(getViewLifecycleOwner(),chatListObserver );
        chatViewModel.refreshChatList();

        final Observer<String> roomIdObserver  = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String chatList) {

                Intent intent = new Intent(getContext(), Activity_Chat_Room.class);
                intent.putExtra("roomId", chatList);//실제 작동 방식
                //intent.putExtra("roomId", "133a8c93-7952-4e7d-8891-dc4758f554eb");//테스트용 roomId
                startActivity(intent);

            }
        };
        profileViewModel.getRoomId().observe(getViewLifecycleOwner(),roomIdObserver );
    }

}
