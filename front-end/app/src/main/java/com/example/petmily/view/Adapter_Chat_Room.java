package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.ChatListBinding;
import com.example.petmily.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Chat_Room extends RecyclerView.Adapter<Adapter_Chat_Room.Holder>{
    List<ChatMessage> list;

    public Adapter_Chat_Room(List<ChatMessage> list) {
        this.list = list;
    }

    public void setItemList(List<ChatMessage> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_Chat_Room.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatListBinding chatListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chat_list,
                parent,
                false
        );
        return new Adapter_Chat_Room.Holder(chatListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Chat_Room.Holder holder, int position) {
        ChatMessage chat = list.get(position);

        ImageView imageView = holder.chatListBinding.profileImage;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //그룹페이지 이동
            }
        });
        holder.chatListBinding.setChatMessage(chat);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ChatListBinding chatListBinding;

        public Holder(@NonNull ChatListBinding chatListBinding) {
            super(chatListBinding.getRoot());
            this.chatListBinding=chatListBinding;


        }
    }
}
