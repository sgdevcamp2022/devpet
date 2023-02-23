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
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<PostGrid> gridList;
    private List<Profile> profileList;
    private int POST_NUM;
    int count;
    boolean lodingGrid;
    boolean firstImportGrid;
    private Adapter_PostGrid adapter_postGrid;

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
        firstImportGrid = true;
        init();
        return view;
    }
    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        post = binding.searchPost;
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridList = new ArrayList<PostGrid>();

        binding.searchSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstImportGrid = true;
                postViewModel.postSearch(count);

//                Adapter_PostSearch newAdapter = new Adapter_PostSearch(gridList, Glide.with(context));
//                newAdapter.setOnItemClickListener(new Adapter_PostSearch.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        Intent intent = new Intent(v.getContext(), Activity_PostFull.class);
//                        intent.putExtra("position", position);
//                        startActivity(intent);
//                    }
//                });
//                post = binding.searchPost;
//                post.setLayoutManager(staggeredGridLayoutManager);
//                post.setAdapter(newAdapter);
//                count += 20;

            }
        });


        initObserver();
    }


    public void initObserver()
    {
        final Observer<List<PostGrid>> postGridObserver  = new Observer<List<PostGrid>>() {
            @Override
            public void onChanged(@Nullable final List<PostGrid> postGrids) {
                if(firstImportGrid)
                {
                    adapter_postGrid = new Adapter_PostGrid(postGrids, Glide.with(context));
                    adapter_postGrid.setOnItemClickListener(new Adapter_PostGrid.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Intent intent = new Intent(v.getContext(), Activity_PostFull.class);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    firstImportGrid = false;
                    post.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    post.setAdapter(adapter_postGrid);
                    lodingGrid = false;
                    binding.searchSwipe.setRefreshing(false);
                }
                else
                {
                    adapter_postGrid.additem(postGrids);
                    adapter_postGrid.notifyDataSetChanged();
                    lodingGrid = false;
                    binding.searchSwipe.setRefreshing(false);
                }
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
        final Observer<List<Profile>> profileListObserver  = new Observer<List<Profile>>() {
            @Override
            public void onChanged(@Nullable final List<Profile> profile) {
                if(POST_NUM == profileList.size())
                {
                    postViewModel.postHalf(profile);
                    postViewModel.postGrid(profile);

                }
            }
        };
        profileViewModel.getProfileLiveData().observe(getViewLifecycleOwner(), profileListObserver);

        postViewModel.postSearch(count);
        //count+=20;

        post.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 리사이클러뷰 가장 마지막 index
                int x[] = new int[5];
                int[] lastPosition = staggeredGridLayoutManager.findFirstVisibleItemPositions(x);

                // 받아온 리사이클러 뷰 카운트
                int totalCount = recyclerView.getAdapter().getItemCount();

                StaggeredGridLayoutManager layoutManager =
                        StaggeredGridLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int[] lastVisible = layoutManager.findLastCompletelyVisibleItemPositions(new int[5]);
                Log.e("totalcount:", lastVisible[0]+"");
                // 스크롤을 맨 끝까지 한 것!
                if(!lodingGrid) {
                    if (lastVisible[0] >= totalItemCount - 8) {

                        postViewModel.postImport(count);
                        count += 10;
                        lodingGrid = true;
                        binding.searchSwipe.setRefreshing(true);
                    }
                }
            }
        });



    }

}
