package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentMessageBinding;
import com.example.petmily.model.data.chat.list.Chat_List;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.remote.API_Interface;
import com.example.petmily.model.data.chat.room.remote.ChatRoomMake;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Message extends Fragment {

    private FragmentMessageBinding binding;
    private Context context;
    private Gson gson;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_message, container, false);
        View view = binding.getRoot();
        context = container.getContext();
        gson = new GsonBuilder().setLenient().create();

        RecyclerView messageList = binding.messageList;
        messageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ArrayList<Chat_List> test = new ArrayList<Chat_List>();
        test.add(new Chat_List(R.id.profile_image, "이름", "대충 알림 내용"));

        Adapter_Message adapter_message = new Adapter_Message(test);
        adapter_message.setOnItemClickListener(new Adapter_Message.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int pos, String roomId) {

                //String url = "http://121.187.22.37:8080/chat/";
                String url = "http://10.0.2.2:4444/chat/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                API_Interface chatInterface = retrofit.create(API_Interface.class);

                List<String> userId = new ArrayList<>();
                userId.add("1");//내 이메일
                userId.add("2");//상대 이메일

                Call<ChatRoomMake> testCallback = chatInterface.createRoom(userId);
                testCallback.enqueue(new retrofit2.Callback<ChatRoomMake>(){

                    @Override
                    public void onResponse(Call<ChatRoomMake> call, Response<ChatRoomMake> response) {

                        ChatRoomMake result = response.body();
                        //chatRoomViewModel.sendMessage(result.getRoomId()+"");
                        //Toast.makeText(ChatService.this, result.getRoomId()+"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), Activity_Chat_Room.class);
                        //intent.putExtra("roomId", result.getRoomId());//실제 작동 방식
                        intent.putExtra("roomId", "133a8c93-7952-4e7d-8891-dc4758f554eb");//테스트용 roomId
                        startActivity(intent);

                    }
                    @Override
                    public void onFailure(Call<ChatRoomMake> call, Throwable t) {
                        Log.e("방 생성 실패 : ",t.getMessage());
                    }
                });
            }
        });
        messageList.setAdapter(adapter_message);
        return view;
    }
}
