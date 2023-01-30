package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;

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
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "1"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "2"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "3"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "4"));
        test.add(new Message(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용", "5"));

        Adapter_Message adapter_message = new Adapter_Message(test);
        messageList.setAdapter(adapter_message);

        return view;
    }
}
