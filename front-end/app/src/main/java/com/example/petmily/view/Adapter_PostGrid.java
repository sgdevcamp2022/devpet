package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListGridBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.remote.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    RequestManager glide;

    public Adapter_PostGrid(List<PostGrid> list, RequestManager glide) {
        this.list = list;
        this.glide = glide;
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


        glide.load(post.getUri()).into(holder.postListBinding.postImage);

        String content = holder.postListBinding.text.getText().toString();
        holder.postListBinding.text.setText(setTag(content));


        holder.postListBinding.postImage.setClipToOutline(true);
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
            postListBinding.text.setMovementMethod(LinkMovementMethod.getInstance());

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


    private SpannableString setTag(String tag) {
        int i;

        ArrayList<int[]> hashTag = getSpans(tag, '#', '@');

        SpannableString tagsContent = new SpannableString(tag);

        for (i = 0; i < hashTag.size(); i++) {
            int[] hashSpan = hashTag.get(i);
            int hashTagStart = hashSpan[0]-1;
            int hashTagEnd = hashSpan[1];

            Adapter_PostFull.HashTag hash = new Adapter_PostFull.HashTag(context);
            hash.setOnClickEventListener(new Adapter_PostFull.HashTag.ClickEventListener() {
                @Override
                public void onClickEvent(String data) {
                    if(data.charAt(0) == '#')
                    {

                    }
                    else if(data.charAt(0) == '@')
                    {
                        Intent intent = new Intent(context, Activity_Profile.class);
                        intent.putExtra("userId", data.substring(1, data.length()));
                        context.startActivity(intent);
                    }
                    Log.e("클릭 : ", data);
                }
            });

            tagsContent.setSpan(hash, hashTagStart, hashTagEnd, 0);
        }


        return tagsContent;
    }

    public ArrayList<int[]> getSpans(String body, char prefix, char prefix2) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");

        Pattern pattern2 = Pattern.compile(prefix2 + "\\w+");

        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }
        matcher = pattern2.matcher(body);
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return  spans;
    }
    public static class HashTag extends ClickableSpan {

        public interface ClickEventListener{
            void onClickEvent(String data);
        }

        private Adapter_PostFull.HashTag.ClickEventListener mClickEventListener = null;

        Context context;
        TextPaint textPaint;

        public HashTag(Context context) {
            super();
            this.context = context;
        }

        public void setOnClickEventListener(Adapter_PostFull.HashTag.ClickEventListener listener)
        {
            mClickEventListener = listener;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            textPaint = ds;
            ds.setColor(ds.linkColor);
            ds.setARGB(255, 30, 144, 255);
        }

        @Override
        public void onClick(View widget) {
            TextView tv = (TextView) widget;
            Spanned s = (Spanned) tv.getText();
            int start = s.getSpanStart(this);
            int end = s.getSpanEnd(this);
            String theWord = s.subSequence(start + 1, end).toString();
            mClickEventListener.onClickEvent(theWord);
        }
    }

}
