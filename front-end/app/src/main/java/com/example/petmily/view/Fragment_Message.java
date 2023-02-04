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
import com.example.petmily.model.Alarm;
import com.example.petmily.model.Message;
import com.example.petmily.model.TestChatRoom;
import com.example.petmily.model.TestInterface;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_message, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        RecyclerView messageList = binding.messageList;
        messageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ArrayList<Message> test = new ArrayList<Message>();
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "1", "testid"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "2", "testid2"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "3", "testid3"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "4", "testid4"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "5", "testid5"));





        Adapter_Message adapter_message = new Adapter_Message(test);
        adapter_message.setOnItemClickListener(new Adapter_Message.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int pos, String roomId) {


                Gson gson = new GsonBuilder().setLenient().create();
                String url = "http://10.0.2.2:4444/chat/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                TestInterface testInterface = retrofit.create(TestInterface.class);

                List<String> userId = new ArrayList<>();
                userId.add("1");
                userId.add("2");






                Call<TestChatRoom> testCallback = testInterface.createRoom(userId);
                testCallback.enqueue(new retrofit2.Callback<TestChatRoom>(){

                    @Override
                    public void onResponse(Call<TestChatRoom> call, Response<TestChatRoom> response) {



                        //Log.e("call 결과 테스트 : ", call.request()+"");
                        //Log.e("결과 테스트 : ", response.code()+"");
                        TestChatRoom result = response.body();




                        //chatRoomViewModel.sendMessage(result.getRoomId()+"");


                        //Log.e("결과 테스트 : ", result.getRoomId()+"");


                        //Toast.makeText(ChatService.this, result.getRoomId()+"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), Activity_Chat_Room.class);
                        //intent.putExtra("roomId", result.getRoomId());
                        intent.putExtra("roomId", "133a8c93-7952-4e7d-8891-dc4758f554eb");


                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<TestChatRoom> call, Throwable t) {
                        Log.e("hhh","ggg",t);
                    }



                });






            }
        });


        messageList.setAdapter(adapter_message);





        return view;
    }
}
