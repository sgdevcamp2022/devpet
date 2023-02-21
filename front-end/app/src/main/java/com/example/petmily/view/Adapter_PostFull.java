package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListFullBinding;
import com.example.petmily.databinding.PostListGridBinding;
import com.example.petmily.model.data.post.PostFull;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.remote.Post;

import java.util.List;

public class Adapter_PostFull extends RecyclerView.Adapter<Adapter_PostFull.Holder>{

    List<PostFull> list;
    Context context;

    public Adapter_PostFull(List<PostFull> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_PostFull.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListFullBinding postListFullBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list_full,
                parent,
                false
        );
        context = parent.getContext();
        return new Adapter_PostFull.Holder(postListFullBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PostFull.Holder holder, int position) {
        PostFull post = list.get(position);

        holder.postListBinding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Profile.class);
                intent.putExtra("userId", post.getUserId());
                context.startActivity(intent);
            }
        });
        Glide.with(context)
                .load(post.getImageUrl().get(0))
                .into(holder.postListBinding.postImage);
        holder.postListBinding.setPost(post);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private PostListFullBinding postListBinding;

        public Holder(@NonNull PostListFullBinding postListBinding) {
            super(postListBinding.getRoot());
            this.postListBinding=postListBinding;
            postListBinding.postImage.setImageResource(R.drawable.ic_launcher_background);

        }
    }

    public void setList(List<PostFull> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }
}
