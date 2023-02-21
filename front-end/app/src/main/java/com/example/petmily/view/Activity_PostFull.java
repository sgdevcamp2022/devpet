package com.example.petmily.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityPostFullBinding;
import com.example.petmily.model.data.post.PostFull;
import com.example.petmily.model.data.post.remote.Post;
import com.example.petmily.viewModel.PostViewModel;

import java.util.List;

public class Activity_PostFull extends AppCompatActivity {
    private ActivityPostFullBinding binding;
    private PostViewModel postViewModel;
    private RecyclerView post;
    private int position;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_full);
        binding.setPost(this);

        position = getIntent().getIntExtra("position", -1);
        time = System.currentTimeMillis();
        init();

    }
    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.init();
        post = binding.postFull;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        post.setLayoutManager(linearLayoutManager);
        post.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                postViewModel.actionAdd(time, linearLayoutManager.findFirstVisibleItemPosition());
                time  = System.currentTimeMillis();
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });


        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<PostFull>> getPostFullObserver = new Observer<List<PostFull>>() {
            @Override
            public void onChanged(List<PostFull> posts) {
                Adapter_PostFull newAdapter = new Adapter_PostFull(posts);
                post.setAdapter(newAdapter);
            }
        };
        postViewModel.getPostFull().observe(this, getPostFullObserver);
        postViewModel.postFull();
    }

    @Override
    public void onDestroy() {
        //postViewModel.postAction(time, false, false, position);

        super.onDestroy();
    }

}