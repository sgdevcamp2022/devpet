package com.example.petmily.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListGridBinding;
import com.example.petmily.model.data.post.PostGrid;

import java.util.List;

public class Adapter_PostGrid extends RecyclerView.Adapter<Adapter_PostGrid.Holder>{
    List<PostGrid> list;
    Context context;

    public Adapter_PostGrid(List<PostGrid> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_PostGrid.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListGridBinding postListGridBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list_grid,
                parent,
                false
        );
        context = parent.getContext();
        return new Adapter_PostGrid.Holder(postListGridBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PostGrid.Holder holder, int position) {
        PostGrid post = list.get(position);


        Glide.with(context)
                .load(post.getUri())
                .into(holder.postListBinding.postImage);

        Log.e("이미지 뷰 체크 : ", post.getUri().toString());



        holder.postListBinding.setPostGrid(post);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private PostListGridBinding postListBinding;

        public Holder(@NonNull PostListGridBinding postListBinding) {
            super(postListBinding.getRoot());
            this.postListBinding=postListBinding;
            //postListBinding.postImage.setImageResource(R.drawable.ic_launcher_background);


        }
    }

    public void setList(List<PostGrid> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }
}
