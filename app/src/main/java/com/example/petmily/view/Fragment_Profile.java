package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.FragmentProfileBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.model.data.profile.remote.SuccessFollow;
import com.example.petmily.model.data.profile.remote.SuccessFollower;
import com.example.petmily.viewModel.PostViewModel;
import com.example.petmily.viewModel.ProfileViewModel;

import java.util.List;

public class Fragment_Profile extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;

    private RecyclerView post;
    private PostViewModel postViewModel;
    private ProfileViewModel profileViewModel;


    public String nickname;
    public String about;
    public String birth;
    public String follow;
    public String follower;

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
        postViewModel.init();

        post = binding.searchPost;
        post.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        initObserver();
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
        postViewModel.getPostGrid().observe(getViewLifecycleOwner(), postGridObserver);

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
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profileObserver);

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

        profileViewModel.profileImport();
    }

}
