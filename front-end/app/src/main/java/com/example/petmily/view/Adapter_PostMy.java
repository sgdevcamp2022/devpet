package com.example.petmily.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListHalfBinding;
import com.example.petmily.databinding.PostListMyBinding;
import com.example.petmily.model.data.post.PostHalf;
import com.example.petmily.model.data.post.PostMy;

import java.util.List;

public class Adapter_PostMy extends RecyclerView.Adapter<Adapter_PostMy.Holder>{

    List<PostMy> list;
    Context context;
    RequestManager glide;

    public Adapter_PostMy(List<PostMy> list, RequestManager glide) {
        this.list = list;
        this.glide = glide;
    }

    @NonNull
    @Override
    public Adapter_PostMy.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListMyBinding postListHalfBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list_my,
                parent,
                false
        );
        context = parent.getContext();
        return new Adapter_PostMy.Holder(postListHalfBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PostMy.Holder holder, int position) {
        PostMy post = list.get(position);

        glide.load(post).into(holder.postListMyBinding.postImage);

        holder.postListMyBinding.postImage.setClipToOutline(true);
        holder.postListMyBinding.setPostMy(post);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

class Holder extends RecyclerView.ViewHolder {
    private PostListMyBinding postListMyBinding;

    public Holder(@NonNull PostListMyBinding postListMyBinding) {
        super(postListMyBinding.getRoot());
        this.postListMyBinding = postListMyBinding;
        postListMyBinding.postImage.setClipToOutline(true);
    }
}
    public void setList(List<PostMy> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}