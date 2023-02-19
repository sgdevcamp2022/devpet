
package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityProfileBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.model.data.profile.remote.SuccessFollow;
import com.example.petmily.model.data.profile.remote.SuccessFollower;
import com.example.petmily.view.Activity_Chat;
import com.example.petmily.view.Adapter_PostGrid;
import com.example.petmily.viewModel.PostViewModel;
import com.example.petmily.viewModel.ProfileViewModel;


import java.util.List;

public class Activity_Profile extends AppCompatActivity {
    private ActivityProfileBinding binding;

    private RecyclerView post;
    private PostViewModel postViewModel;
    private ProfileViewModel profileViewModel;
    private String userId;


    public String nickname;
    public String about;
    public String birth;
    public String follow;
    public String follower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setProfile(this);

        init();
    }




    public void init()
    {
        userId = getIntent().getStringExtra("userId");

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.init();



        initView();
    }

    public void initView()
    {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        post = binding.searchPost;
        post.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel.createChatRoom(userId);
            }
        });
    }

    public void initObserver()
    {
        final Observer<List<PostGrid>> postGridObserver  = new Observer<List<PostGrid>>() {
            @Override
            public void onChanged(@Nullable final List<PostGrid> postGrids) {
                Adapter_PostGrid newAdapter = new Adapter_PostGrid(postGrids);
                post.setAdapter(newAdapter);
            }
        };
        postViewModel.getPostGrid().observe(this, postGridObserver);

        final Observer<Profile> profileObserver  = new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable final Profile profile) {
//                nickname = profile.getNickname();
//                about = profile.getAbout();
                binding.nickname.setText(profile.getNickname());
                binding.about.setText(profile.getAbout());
                birth = profile.getBirth();
//                Glide.with(context)
//                        .load(profile.getImageUri())
//                        .into(binding.profileImage);

            }
        };
        profileViewModel.getProfile().observe(this, profileObserver);

        final Observer<SuccessFollow> followObserver  = new Observer<SuccessFollow>() {
            @Override
            public void onChanged(@Nullable final SuccessFollow result) {
                binding.followNum.setText(result.getResult().size()+"");
                follow = result.getResult().size()+"";
            }
        };
        profileViewModel.getFollow().observe(this, followObserver);

        final Observer<SuccessFollower> followerObserver  = new Observer<SuccessFollower>() {
            @Override
            public void onChanged(@Nullable final SuccessFollower result) {
                follower = result.getResult().size()+"";
                binding.followerNum.setText(result.getResult().size()+"");

            }
        };
        profileViewModel.getFollower().observe(this, followerObserver);

        final Observer<String> roomIdObserver  = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String result) {
                Intent i = new Intent(getApplicationContext(), Activity_Chat.class);
                i.putExtra("roomId", result);
                startActivity(i);
            }
        };
        profileViewModel.getRoomId().observe(this, roomIdObserver);

        final Observer<Boolean> followEventObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    binding.followButton.setImageResource(R.drawable.follow_check);
                }
                else
                {
                    binding.followButton.setImageResource(R.drawable.follow);
                }

            }
        };
        profileViewModel.getFollowEvent().observe(this, followEventObserver);

        profileViewModel.profileImport(userId);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
