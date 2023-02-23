package com.example.petmily.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.FragmentProfileBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostMy;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.model.data.profile.remote.SuccessFollow;
import com.example.petmily.model.data.profile.remote.SuccessFollower;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.PostViewModel;
import com.example.petmily.viewModel.ProfileViewModel;
import com.example.petmily.viewModel.service.ChatService;
import com.example.petmily.viewModel.service.ChatWorker;
import com.example.petmily.viewModel.service.UndeadService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Profile extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;

    private RecyclerView post;
    private PostViewModel postViewModel;
    private ProfileViewModel profileViewModel;


    private List<Profile> profileList;
    public String nickname;
    public String about;
    public String birth;
    public String follow;
    public String follower;
    private String token;
    private String username;
    private String userId;
    private int POST_NUM = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        init();

        return view;
    }
    public void init()
    {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("email", "");
        userId = sharedPreferences.getString("userId", "");
        profileList = new ArrayList<>();



        initView();
    }

    public void initView()
    {
        post = binding.searchPost;
        post.setLayoutManager(new GridLayoutManager(context, 3));
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ChatWorker.isRunning = false;
//                UndeadService.isRunning = false;
//                ChatService.isRunning = false;
                binding.drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });


        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationViewModel.logout();
            }
        });

        binding.tagPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_MakeProfile.class);
                intent.putExtra("replace", true);
                startActivity(intent);
            }
        });
        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<PostMy>> postMyObserver  = new Observer<List<PostMy>>() {
            @Override
            public void onChanged(@Nullable final List<PostMy> postGrids) {
                Adapter_PostMy newAdapter = new Adapter_PostMy(postGrids, Glide.with(context));
                post.setLayoutManager(new GridLayoutManager(context, 3));
                post.setAdapter(newAdapter);
            }
        };
        postViewModel.getPostMyList().observe(getViewLifecycleOwner(), postMyObserver);

        final Observer<Profile> profileObserver  = new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable final Profile profile) {
                profileList.add(profile);


            }
        };
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profileObserver);


        final Observer<Profile> profileLoginObserver  = new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable final Profile profile) {
                if(profile != null)
                {
                    binding.nickname.setText(profile.getNickname());
                    binding.about.setText(profile.getAbout());
                    birth = profile.getBirth();
                }
                else
                {
                    Intent intent = new Intent(context, Activity_MakeProfile.class);
                    startActivity(intent);
                    Log.e("프로필 정보가 없어 프로필 화면으로 이동 : ", "null");

                }

            }
        };
        profileViewModel.getProfileLigin().observe(getViewLifecycleOwner(), profileLoginObserver);


        final Observer<List<Profile>> profileListObserver  = new Observer<List<Profile>>() {
            @Override
            public void onChanged(@Nullable final List<Profile> profile) {
                if(POST_NUM == profileList.size())
                {
                    postViewModel.postGrid(profile);
                   // binding.postGridListSwipe.setRefreshing(false);
                }
            }
        };
        profileViewModel.getProfileLiveData().observe(getViewLifecycleOwner(), profileListObserver);

        final Observer<SuccessFollow> followObserver  = new Observer<SuccessFollow>() {
            @Override
            public void onChanged(@Nullable final SuccessFollow result) {
                binding.followNum.setText(result.getResult().size()+"");
                follow = result.getResult().size()+"";
            }
        };
        profileViewModel.getFollow().observe(getViewLifecycleOwner(), followObserver);

        final Observer<SuccessFollower> followerObserver  = new Observer<SuccessFollower>() {
            @Override
            public void onChanged(@Nullable final SuccessFollower result) {
                follower = result.getResult().size()+"";
                binding.followerNum.setText(result.getResult().size()+"");

            }
        };
        profileViewModel.getFollower().observe(getViewLifecycleOwner(), followerObserver);


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
        postViewModel.getUserIdLiveData().observe(getViewLifecycleOwner(), userIdLivaDataObserver);


        profileViewModel.profileMyImport();
        //profileViewModel.profileFollow(userId);
        //profileViewModel.profileFollower(userId);
        postViewModel.postMy();

    }


}
