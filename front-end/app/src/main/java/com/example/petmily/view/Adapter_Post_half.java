package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.PostListHalfBinding;
import com.example.petmily.model.Post_half;

import java.util.ArrayList;


public class Adapter_Post_half extends RecyclerView.Adapter<Adapter_Post_half.Holder>{

    ArrayList<Post_half> list;

    public Adapter_Post_half(ArrayList<Post_half> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Post_half.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListHalfBinding postListHalfBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list_half,
                parent,
                false
        );
        return new Adapter_Post_half.Holder(postListHalfBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Post_half.Holder holder, int position) {
        Post_half postHalf = list.get(position);
        holder.postListHalfBinding.setPostHalf(postHalf);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    class Holder extends RecyclerView.ViewHolder {
        private PostListHalfBinding postListHalfBinding;

        public Holder(@NonNull PostListHalfBinding postListHalfBinding) {
            super(postListHalfBinding.getRoot());
            this.postListHalfBinding=postListHalfBinding;
        }
    }

}
