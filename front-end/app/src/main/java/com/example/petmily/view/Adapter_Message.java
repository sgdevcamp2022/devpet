package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.MessageListBinding;
import com.example.petmily.model.data.chat.list.ChatList;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Message extends RecyclerView.Adapter<Adapter_Message.Holder>{

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String roomId) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }
    List<ChatList> list;

    public Adapter_Message(List<ChatList> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Message.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageListBinding messageListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.message_list,
                parent,
                false
        );
        return new Adapter_Message.Holder(messageListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Message.Holder holder, int position) {
        ChatList message = list.get(position);
        holder.messageListBinding.setChatList(message);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private MessageListBinding messageListBinding;

        public Holder(@NonNull MessageListBinding messageListBinding) {
            super(messageListBinding.getRoot());
            this.messageListBinding=messageListBinding;

            messageListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //???????????? ??????????????? ??????
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //?????? ?????? (onItemClick ?????? ??????)
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(v, pos, messageListBinding.getChatList().getRoodId());

                        }
                    }
                }
            });

        }
    }

}