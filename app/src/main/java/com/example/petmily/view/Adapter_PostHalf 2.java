package com.example.petmily.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListHalfBinding;
import com.example.petmily.model.data.post.PostHalf;

import java.util.List;


public class Adapter_PostHalf extends RecyclerView.Adapter<Adapter_PostHalf.Holder>{

    List<PostHalf> list;
    Context context;


    public Adapter_PostHalf(List<PostHalf> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_PostHalf.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListHalfBinding postListHalfBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list_half,
                parent,
                false
        );
        context = parent.getContext();
        return new Adapter_PostHalf.Holder(postListHalfBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PostHalf.Holder holder, int position) {
        PostHalf post = list.get(position);
        Glide.with(context)
                .load(post.getImageUri())
                .into(holder.postListHalfBinding.postImage);



        holder.postListHalfBinding.setPostHalf(post);

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
            //postListHalfBinding.postImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    public void setList(List<PostHalf> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

}
