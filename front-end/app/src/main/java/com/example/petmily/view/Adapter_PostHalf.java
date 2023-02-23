package com.example.petmily.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }



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
        holder.postListHalfBinding.postImage.setClipToOutline(true);

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

            postListHalfBinding.getRoot().setOnClickListener(new View.OnClickListener() {
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

    public void setList(List<PostHalf> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

}
