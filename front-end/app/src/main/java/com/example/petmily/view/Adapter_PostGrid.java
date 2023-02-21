package com.example.petmily.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListGridBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.remote.Post;

import java.util.List;

public class Adapter_PostGrid extends RecyclerView.Adapter<Adapter_PostGrid.Holder>{

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

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


        holder.postListBinding.setPostGrid(post);
        holder.postListBinding.postImage.setClipToOutline(true);

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

            postListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //존재하는 포지션인지 확인
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //동작 호출 (onItemClick 함수 호출)
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(v, pos);

                        }
                    }
                }
            });

        }
    }

    public void setList(List<PostGrid> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }
}
