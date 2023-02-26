package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.Code;
import com.example.petmily.R;
import com.example.petmily.databinding.ChatListBinding;
import com.example.petmily.databinding.ChatListMyBinding;
import com.example.petmily.model.data.chat.room.Message;

import java.util.List;



public class Adapter_Chat_Room extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Message> list;

    public Adapter_Chat_Room(List<Message> list) {
        this.list = list;
    }

    public void setItemList(List<Message> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == Code.ViewType.LEFT_CONTENT)
        {
            ChatListMyBinding chatListBinding= DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.chat_list_my,
                    parent,
                    false
            );
            return new Adapter_Chat_Room.Holder1(chatListBinding);
        }
        else
        {
            ChatListBinding chatListBinding= DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.chat_list,
                    parent,
                    false
            );
            return new Adapter_Chat_Room.Holder(chatListBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Holder)
        {
            Message chat = list.get(position);

            ImageView imageView = ((Holder) holder).chatListBinding.profileImage;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //그룹페이지 이동
                }
            });
            ((Holder) holder).chatListBinding.setMessage(chat);
        }
        else if(holder instanceof Holder1)
        {
            Message chat = list.get(position);
            ((Holder1) holder).chatListMyBinding.setMessage(chat);
        }

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

    class Holder1 extends RecyclerView.ViewHolder {
        private ChatListMyBinding chatListMyBinding;


        public Holder1(@NonNull ChatListMyBinding chatListMyBinding) {
            super(chatListMyBinding.getRoot());
            this.chatListMyBinding=chatListMyBinding;

        }
    }
}
