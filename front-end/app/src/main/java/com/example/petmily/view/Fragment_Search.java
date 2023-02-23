package com.example.petmily.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.FragmentSearchBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.viewModel.PostViewModel;
import com.example.petmily.viewModel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Search extends Fragment {

    private FragmentSearchBinding binding;
    private Context context;

    private RecyclerView post;
    private PostViewModel postViewModel;
    private ProfileViewModel profileViewModel;
    List<PostGrid> gridList;
    private List<Profile> profileList;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int POST_NUM;
    int count;
    boolean lodingGrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search, container, false);
        View view = binding.getRoot();
        context = container.getContext();
        count = 0;
        POST_NUM = 20;
        lodingGrid = false;
        init();
        return view;
    }
    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        post = binding.searchPost;
        post.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        binding.searchSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postViewModel.postSearch(count);
                count += 20;

            }
        });
        gridList = new ArrayList<PostGrid>();

        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<PostGrid>> postGridObserver  = new Observer<List<PostGrid>>() {
            @Override
            public void onChanged(@Nullable final List<PostGrid> postGrids) {
                Adapter_PostGrid newAdapter = new Adapter_PostGrid(postGrids, Glide.with(context));
                newAdapter.setOnItemClickListener(new Adapter_PostGrid.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(v.getContext(), Activity_PostFull.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });
                post = binding.searchPost;
                post.setLayoutManager(staggeredGridLayoutManager);
                post.setAdapter(newAdapter);
                lodingGrid = false;
                binding.searchSwipe.setRefreshing(false);
            }
        };
        postViewModel.getPostGrid().observe(getViewLifecycleOwner(), postGridObserver);

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

        final Observer<Profile> profileObserver  = new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable final Profile profile) {
                profileList.add(profile);
            }
        };
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profileObserver);

        postViewModel.postSearch(count);
        //count+=20;
    }

}
