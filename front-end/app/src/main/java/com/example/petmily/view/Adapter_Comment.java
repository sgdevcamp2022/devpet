package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.CommentListBinding;
import com.example.petmily.databinding.PlaceListBinding;
import com.example.petmily.model.Place;
import com.example.petmily.model.data.post.remote.Result;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Comment extends RecyclerView.Adapter<Adapter_Comment.Holder>{

    List<Result> list;

    public Adapter_Comment(List<Result> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Comment.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentListBinding commentListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.place_list,
                parent,
                false
        );
        return new Adapter_Comment.Holder(commentListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Comment.Holder holder, int position) {
        Result result = list.get(position);
        holder.commentListBinding.setComment(result);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private CommentListBinding commentListBinding;

        public Holder(@NonNull CommentListBinding commentListBinding) {
            super(commentListBinding.getRoot());
            this.commentListBinding=commentListBinding;
        }
    }

}
