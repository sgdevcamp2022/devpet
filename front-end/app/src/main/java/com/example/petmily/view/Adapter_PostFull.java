package com.example.petmily.view;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.petmily.R;
import com.example.petmily.databinding.PostListFullBinding;
import com.example.petmily.databinding.PostListGridBinding;
import com.example.petmily.model.data.post.PostFull;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostHalf;
import com.example.petmily.model.data.post.remote.Post;
import com.example.petmily.model.data.post.remote.Result;
import com.example.petmily.viewModel.PostViewModel;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Adapter_PostFull extends RecyclerView.Adapter<Adapter_PostFull.Holder> {

    List<PostFull> list;
    Context context;
    PostViewModel postViewModel;
    String userId;
    RequestManager glide;
    LinearLayoutManager linearLayoutManager;
    int feedId;

    public Adapter_PostFull(List<PostFull> list, RequestManager glide) {
        this.list = list;
        this.glide = glide;
    }

    @NonNull
    @Override
    public Adapter_PostFull.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListFullBinding postListFullBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_list_full,
                parent,
                false
        );
        context = parent.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        postViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(PostViewModel.class);
        feedId = 0;
        return new Adapter_PostFull.Holder(postListFullBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PostFull.Holder holder, int position) {
        PostFull post = list.get(position);
        holder.postListBinding.setPost(post);


        feedId = post.getFeedId();
        if (post.isFavorite()) {
            holder.postListBinding.like.setImageResource(R.drawable.heart_touch);
        } else {
            holder.postListBinding.like.setImageResource(R.drawable.heart);

        }
        holder.postListBinding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Profile.class);
                intent.putExtra("userId", list.get(position).getUserId()+"");

                context.startActivity(intent);
            }
        });
        holder.postListBinding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!list.get(position).isFavorite()) {
                    holder.postListBinding.like.setImageResource(R.drawable.heart_touch);
                    list.get(position).setFavorite(true);
                    postViewModel.likeSelect(list.get(position).getFeedId());

                } else {
                    holder.postListBinding.like.setImageResource(R.drawable.heart);
                    list.get(position).setFavorite(false);
                    postViewModel.likeSelect(list.get(position).getFeedId());
                }

            }
        });
        holder.postListBinding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Comment.class);
                intent.putExtra("nickname", holder.postListBinding.nickname.getText());
                intent.putExtra("time", holder.postListBinding.time.getText());
                intent.putExtra("coment", list.get(position).getContent());
                intent.putExtra("feedId", post.getFeedId());
                context.startActivity(intent);
            }
        });
        Adapter_ViewPager2 adapter_viewPager2 = new Adapter_ViewPager2(context, post.getImageUrl());
        holder.postListBinding.postImage.setAdapter(adapter_viewPager2);


        holder.postListBinding.removeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);//v는 클릭된 뷰를 의미

                popup.getMenuInflater().inflate(R.menu.post_remove_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove:
                                postViewModel.removePost(list.get(position).getFeedId());
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
        holder.postListBinding.coment.setText(setTag(post.getContent()));

        holder.postListBinding.setPost(post);
        Log.e("" + post.getUserId() + "\t", userId);
        if ((post.getUserId() + "").equals(userId)) {
            holder.postListBinding.removeMenu.setVisibility(View.VISIBLE);
        }





    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends ViewHolder {
        private PostListFullBinding postListBinding;

        public Holder(@NonNull PostListFullBinding postListBinding) {
            super(postListBinding.getRoot());
            this.postListBinding = postListBinding;
            postListBinding.profileImage.setClipToOutline(true);
            postListBinding.coment.setMovementMethod(LinkMovementMethod.getInstance());
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            postListBinding.postImage.setLayoutManager(linearLayoutManager);
        }

    }
    public int getFeedId()
    {
        return feedId;
    }

    public void setList(List<PostFull> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private SpannableString setTag(String tag) {
        int i;

        ArrayList<int[]> hashTag = getSpans(tag, '#', '@');

        SpannableString tagsContent = new SpannableString(tag);

        for (i = 0; i < hashTag.size(); i++) {
            int[] hashSpan = hashTag.get(i);
            int hashTagStart = hashSpan[0];
            int hashTagEnd = hashSpan[1];

            HashTag hash = new HashTag(context);
            hash.setOnClickEventListener(new HashTag.ClickEventListener() {
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

        private ClickEventListener mClickEventListener = null;

        Context context;
        TextPaint textPaint;

        public HashTag(Context context) {
            super();
            this.context = context;
        }

        public void setOnClickEventListener(ClickEventListener listener)
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
