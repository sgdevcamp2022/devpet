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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentGroupBinding;
import com.example.petmily.model.Post;

import java.util.ArrayList;

public class Fragment_Group extends Fragment {

    private FragmentGroupBinding binding;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_group, container, false);
        View view = binding.getRoot();
        context = container.getContext();



        RecyclerView post = binding.groupPost;
        post.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ArrayList<Post> test = new ArrayList<Post>();
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));
        test.add(new Post(R.drawable.ic_launcher_background, "닉네임", "대충 게시글 내용", "몇 시간 전"));


        /*
        Adapter_PostGrid adapter_postGrid = new Adapter_PostGrid(test);
        post.setAdapter(adapter_postGrid);

         */



        return view;
    }

}
