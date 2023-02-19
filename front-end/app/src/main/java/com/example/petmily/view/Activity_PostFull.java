package com.example.petmily.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityPostFullBinding;
import com.example.petmily.model.data.post.remote.Post;
import com.example.petmily.viewModel.PostViewModel;

import java.util.List;

public class Activity_PostFull extends AppCompatActivity {
    private ActivityPostFullBinding binding;
    private PostViewModel postViewModel;
    private RecyclerView post;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_full);
        binding.setPost(this);

        position = getIntent().getIntExtra("position", -1);

        init();

    }
    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.init();


        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<Post>> getPostFullObserver = new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                Adapter_PostFull newAdapter = new Adapter_PostFull(posts);
                post.setAdapter(newAdapter);
            }
        };
        postViewModel.getPostFull().observe(this, getPostFullObserver);
        postViewModel.postFull();
    }

}