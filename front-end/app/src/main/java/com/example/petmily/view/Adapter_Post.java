package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.AlarmListBinding;
import com.example.petmily.databinding.PostListBinding;
import com.example.petmily.model.Alarm;
import com.example.petmily.model.Post;

import java.util.ArrayList;

public class Adapter_Post extends RecyclerView.Adapter<Adapter_Post.Holder>{
    ArrayList<Post> list;

    public Adapter_Post(ArrayList<Post> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Post.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListBinding alarmListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list,
                parent,
                false
        );
        return new Adapter_Post.Holder(alarmListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Post.Holder holder, int position) {
        Post post = list.get(position);
        holder.postListBinding.setPost(post);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private PostListBinding postListBinding;

        public Holder(@NonNull PostListBinding postListBinding) {
            super(postListBinding.getRoot());
            this.postListBinding=postListBinding;
        }
    }
}
