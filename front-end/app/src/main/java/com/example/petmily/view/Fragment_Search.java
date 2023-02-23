package com.example.petmily.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
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
import com.example.petmily.viewModel.PostViewModel;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Search extends Fragment {

    private FragmentSearchBinding binding;
    private Context context;

    private RecyclerView post;
    private PostViewModel postViewModel;
    List<PostGrid> gridList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        init();
        return view;
    }
    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        post = binding.searchPost;
        post.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        binding.searchSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postViewModel.postSearch();


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
                post.setAdapter(newAdapter);
                binding.searchSwipe.setRefreshing(false);

            }
        };
        postViewModel.getPostGrid().observe(getViewLifecycleOwner(), postGridObserver);

        postViewModel.postSearch();
    }

}
