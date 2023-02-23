package com.example.petmily.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListHalfBinding;
import com.example.petmily.model.data.post.PostHalf;
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
    RequestManager glide;

    public Adapter_PostHalf(List<PostHalf> list, RequestManager glide) {
        this.list = list;
        this.glide = glide;
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

        glide.load(post.getImageUri()).into(holder.postListHalfBinding.postImage);

        holder.postListHalfBinding.removeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(context, v);//v는 클릭된 뷰를 의미

                popup.getMenuInflater().inflate(R.menu.post_remove_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.remove:
                                list.remove(position);
                                setList(list);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });


        holder.postListHalfBinding.postImage.setClipToOutline(true);
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
            postListHalfBinding.profileImage.setClipToOutline(true);
            postListHalfBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
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

    public void additem(List<PostHalf> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            this.list.add(list.get(i));
        }

    }
}

