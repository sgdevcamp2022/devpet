package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.AlarmListBinding;
import com.example.petmily.databinding.MessageListBinding;
import com.example.petmily.model.Alarm;
import com.example.petmily.model.Message;

import java.util.ArrayList;

public class Adapter_Message extends RecyclerView.Adapter<Adapter_Message.Holder>{
    ArrayList<Message> list;

    public Adapter_Message(ArrayList<Message> list) {
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
        Message message = list.get(position);
        holder.messageListBinding.setMessage(message);
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
        }
    }
}