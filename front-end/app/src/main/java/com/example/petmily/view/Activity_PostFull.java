package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.ActivityPostFullBinding;
import com.example.petmily.model.data.post.PostFull;
import com.example.petmily.model.data.post.remote.Post;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.viewModel.PostViewModel;
import com.example.petmily.viewModel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_PostFull extends AppCompatActivity {

    private ActivityPostFullBinding binding;

    private PostViewModel postViewModel;
    private ProfileViewModel profileViewModel;
    private Context context;

    private RecyclerView post;
    private LinearLayoutManager linearLayoutManager;
    private int position;
    private long time;
    private List<Profile> profileList;
    private int POST_NUM = 0;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_full);
        binding.setPost(this);

        position = getIntent().getIntExtra("position", -1);
        if(position == 0)
        {
            flag = true;
        }
        else
        {
            flag = false;
        }
        time = System.currentTimeMillis();
        context = this;
        init();

    }
    public void init()
    {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        profileList = new ArrayList<>();
        post = binding.postFull;
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        post.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        post.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                if(linearLayoutManager.findFirstVisibleItemPosition() != -1)
                {
                    postViewModel.actionAdd(time, linearLayoutManager.findFirstVisibleItemPosition()+position);
                    time = System.currentTimeMillis();
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
            }
        });

        binding.fullSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postViewModel.postImport();
            }
        });


        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<PostFull>> getPostFullObserver = new Observer<List<PostFull>>() {
            @Override
            public void onChanged(List<PostFull> posts) {
                Adapter_PostFull newAdapter = new Adapter_PostFull(posts, Glide.with(context));
                post.setAdapter(newAdapter);
                post.scrollToPosition(position);
                binding.fullSwipe.setRefreshing(false);
            }
        };
        postViewModel.getPostFull().observe(this, getPostFullObserver);



        final Observer<List<String>> userIdLivaDataObserver = new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> userIdList) {
                profileList = new ArrayList<>();
                profileViewModel.newProfileList();
                POST_NUM = userIdList.size();
                for(int i = 0; i < userIdList.size(); i++)
                {
                    profileViewModel.profileImport(userIdList.get(i));
                }

            }
        };
        postViewModel.getUserIdLiveData().observe(this, userIdLivaDataObserver);

        final Observer<Profile> profileObserver  = new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable final Profile profile) {
                profileList.add(profile);
            }
        };
        profileViewModel.getProfile().observe(this, profileObserver);

        final Observer<List<Profile>> profileListObserver  = new Observer<List<Profile>>() {
            @Override
            public void onChanged(@Nullable final List<Profile> profile) {
                if(POST_NUM == profileList.size())
                {
                    postViewModel.postFull(profile, position);
                    binding.fullSwipe.setRefreshing(false);
                }
            }
        };
        profileViewModel.getProfileLiveData().observe(this, profileListObserver);
        if(flag)
        {
            postViewModel.postSearch("로스트아크");
        }
        else
        {
            postViewModel.postImport();
        }


    }


    @Override
    public void onDestroy() {
        postViewModel.postAction();

        super.onDestroy();
    }





}