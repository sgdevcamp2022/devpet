package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
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

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentSearchBinding;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.viewModel.PostViewModel;

import java.util.List;

public class Fragment_Search extends Fragment {

    private FragmentSearchBinding binding;
    private Context context;

    private RecyclerView post;
    private PostViewModel postViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search, container, false);
        View view = binding.getRoot();
        context = container.getContext();





        return view;
    }
    public void init()
    {
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

        postViewModel.postGrid();
    }

}
