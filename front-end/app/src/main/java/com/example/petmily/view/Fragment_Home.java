package com.example.petmily.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.FragmentHomeBinding;
import com.example.petmily.model.data.post.Entity.Coord;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostHalf;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.viewModel.GpsTracker;
import com.example.petmily.viewModel.PostViewModel;
import com.example.petmily.viewModel.ProfileViewModel;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class Fragment_Home extends Fragment implements OnMapReadyCallback {
    private int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FragmentHomeBinding binding;
    private MapView mapView;
    private static NaverMap naverMap1;

    private FragmentManager fragmentManager;

    private RecyclerView halfView;
    private RecyclerView grid;
    private List<Marker> markerList;
    private List<Profile> profileList;

    private LinearLayout half;

    private PostViewModel postViewModel;
    private ProfileViewModel profileViewModel;

    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private GpsTracker gpsTracker;
    private static SlidingUpPanelLayout sliding;
    public static SlidingUpPanelLayout.PanelState state;
    double latitude;
    double longitude;
    private int POST_NUM = 0;
    private FusedLocationSource locationSource;
    int count;
    boolean lodingHalf;
    boolean lodingGrid;
    boolean firstImportHalf;
    boolean firstImportGrid;
    private Adapter_PostGrid adapter_postGrid;
    private Adapter_PostHalf adapter_postHalf;
    private Bundle savedInstanceState;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        context = container.getContext();
        count = 10;
        permission();
        lodingHalf = false;
        lodingGrid = false;
        firstImportHalf = true;
        firstImportGrid = true;




        gpsTracker = new GpsTracker(context);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        init();

        return view;
    }

    public void init()
    {
        state = SlidingUpPanelLayout.PanelState.HIDDEN;
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();

        halfView = binding.postHalfList;
        grid = binding.postGridList;
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        halfView.setLayoutManager(linearLayoutManager);
        grid.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        grid = binding.postGridList;
        grid.setItemViewCacheSize(100);
        halfView.setItemViewCacheSize(100);





        fragmentManager = getParentFragmentManager();

        half = binding.postHalfLayout;


        binding.postGridListSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postViewModel.postImport();
                firstImportGrid = true;

            }
        });
        binding.postHalfListSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postViewModel.postImport();
                firstImportHalf = true;

            }
        });



        sliding = binding.slidingLayout;
        sliding.setClipToOutline(true);
        sliding.setTouchEnabled(true);
        sliding.setAnchorPoint(0.38F);
        sliding.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                dragAnimation(slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.EXPANDED)
                {
                    grid.setVisibility(View.VISIBLE);
                    half.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha_space);
                    half.startAnimation(animation);
                }
                state = newState;
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
                    grid.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    grid.setAdapter(adapter_postGrid);
                    lodingGrid = false;
                    binding.postGridListSwipe.setRefreshing(false);
                }
                else
                {
                    adapter_postGrid.additem(postGrids);
                    adapter_postGrid.notifyDataSetChanged();
                    lodingGrid = false;
                    binding.postGridListSwipe.setRefreshing(false);
                }

                //postViewModel.setMarker();
            }
        };
        postViewModel.getPostGrid().observe(getViewLifecycleOwner(), postGridObserver);

        final Observer<List<PostHalf>> postHalfObserver  = new Observer<List<PostHalf>>() {
            @Override
            public void onChanged(@Nullable final List<PostHalf> postHalfList) {
                if(firstImportHalf)
                {
                    adapter_postHalf = new Adapter_PostHalf(postHalfList, Glide.with(context));
                    adapter_postHalf.setOnItemClickListener(new Adapter_PostHalf.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Intent intent = new Intent(v.getContext(), Activity_PostFull.class);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                   // halfView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    halfView.setAdapter(adapter_postHalf);
                    lodingGrid = false;
                    binding.postHalfListSwipe.setRefreshing(false);
                    firstImportHalf = false;

                }
                else
                {
                    adapter_postHalf.additem(postHalfList);
                    adapter_postHalf.notifyDataSetChanged();
                    lodingGrid = false;
                    binding.postHalfListSwipe.setRefreshing(false);
                }

              // postViewModel.setMarker();
            }
        };
        postViewModel.getPostHalf().observe(getViewLifecycleOwner(), postHalfObserver);

        final Observer<CameraPosition> cameraPositionObserver  = new Observer<CameraPosition>() {
            @Override
            public void onChanged(@Nullable final CameraPosition cameraPosition) {
                CameraUpdate cameraUpdate = CameraUpdate.toCameraPosition(cameraPosition).animate(CameraAnimation.Easing);
                naverMap1.moveCamera(cameraUpdate);
                naverMap1.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        };
        postViewModel.getCameraPosition().observe(getViewLifecycleOwner(), cameraPositionObserver);

        final Observer<List<Marker>> markerObserver  = new Observer<List<Marker>>() {
            @Override
            public void onChanged(@Nullable final List<Marker> markers) {
                markerList = new ArrayList<>();
                for(int i = 0; i < markers.size(); i++)
                {
                    markerList.add(markers.get(i));
                }

                for(int i = 0; i < markerList.size(); i++)
                {

                    double longitude = markerList.get(i).getPosition().latitude;
                    double latitude = markerList.get(i).getPosition().longitude;
                    markerList.get(i).setPosition(new LatLng(latitude, longitude));
                }
                for(int i = 0; i < markerList.size(); i++)
                {
                    markerList.get(i).setMap(naverMap1);
                }
            }
        };
        postViewModel.getMarkerList().observe(getViewLifecycleOwner(), markerObserver);

        final Observer<String> localNameObserver  = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String local) {
                binding.localname.setText(local);
            }
        };
        postViewModel.getLocalName().observe(getViewLifecycleOwner(), localNameObserver);

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
                if(!profile.getNickname().equals("??????"))
                {
                    profileList.add(profile);
                }

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
                    //postViewModel.setMarker();
                    binding.postGridListSwipe.setRefreshing(false);
                }
            }
        };
        profileViewModel.getProfileLiveData().observe(getViewLifecycleOwner(), profileListObserver);



        final Observer<Boolean> eventPost = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if(aBoolean)
                {
                    postViewModel.postHalf(profileList);
                    postViewModel.postGrid(profileList);
                }
            }
        };
        postViewModel.getPostEvent().observe(getViewLifecycleOwner(), eventPost);

        final Observer<Integer> markerPositionObserver  = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer position) {
                sliding.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                linearLayoutManager = (LinearLayoutManager)halfView.getLayoutManager();
                if(linearLayoutManager != null)
                    linearLayoutManager.scrollToPosition(position);
            }
        };
        postViewModel.getMarkerPosition().observe(getViewLifecycleOwner(), markerPositionObserver);

        postViewModel.postImport();
        lodingGrid = true;
        lodingHalf = true;

        halfView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                postViewModel.moveMap(linearLayoutManager.findFirstVisibleItemPosition());
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {}
        });


        grid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);



                StaggeredGridLayoutManager layoutManager =
                        StaggeredGridLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int[] lastVisible = layoutManager.findLastCompletelyVisibleItemPositions(new int[5]);
                // ???????????? ??? ????????? ??? ???!
                if(!lodingGrid) {
                    if (lastVisible[0] >= totalItemCount - 4) {

                        postViewModel.postImport(count);
                        count += 8;
                        lodingGrid = true;
                    }
                }
            }
        });

        halfView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager layoutManager =
                        LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();

                if(!lodingHalf)
                {
                    if (lastVisible >= totalItemCount - 4) {
                        postViewModel.postImport(count);
                        count += 8;
                        lodingHalf =true;
                        //binding.postHalfListSwipe.setRefreshing(true);
                    }
                }
            }
        });

        grid.setVisibility(View.INVISIBLE);


        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        naverMap1 = naverMap;
        CameraPosition cameraPosition = new CameraPosition(new LatLng(latitude, longitude),18);
        naverMap.setCameraPosition(cameraPosition);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        naverMap.setMapType(NaverMap.MapType.Basic);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(false);
        //uiSettings.setLocationButtonEnabled(true);
        naverMap.addOnLoadListener(new NaverMap.OnLoadListener() {
            @Override
            public void onLoad() {
                postViewModel.setMarker();
                for(int i = 0; i < markerList.size(); i++)
                {
                    double longitude = markerList.get(i).getPosition().latitude;
                    double latitude = markerList.get(i).getPosition().longitude;
                    markerList.get(i).setPosition(new LatLng(latitude, longitude));
                }
                for(int i = 0; i < markerList.size(); i++)
                {
                    markerList.get(i).setMap(naverMap);
                }
                Log.e("???????????????:","");

            }
        });

    }

    public void permission()
    {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.e("????????? ?????? : ", "true");
                Intent i = new Intent();
                String packageName = context.getPackageName();
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

                if (pm.isIgnoringBatteryOptimizations(packageName))
                {
                    //i.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                }
                else {
                    i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    i.setData(Uri.parse("package:" + packageName));
                    startActivity(i);
                }

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Log.e("????????? ?????? : ", "false");
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("???????????? ???????????? ???????????? ????????? ???????????? ?????????.")
                .setDeniedCloseButtonText("??????")
                .setGotoSettingButtonText("??????")
                .setPermissions(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .check();
    }


    public void dragAnimation(float slideOffset)
    {
        if(slideOffset >= 0.0 && slideOffset <= 0.41)
        {
            half.setVisibility(View.VISIBLE);
            grid.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha_full);
            half.startAnimation(animation);
        }
        else if(slideOffset > 0.41 && slideOffset <= 0.45)
        {
            half.setVisibility(View.VISIBLE);
            grid.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha_half);
            half.startAnimation(animation);
        }
        else if(slideOffset > 0.45 && slideOffset < 0.5)
        {
            half.setVisibility(View.VISIBLE);
            grid.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha_half2);
            half.startAnimation(animation);
        }
        else if(slideOffset >= 0.5 && slideOffset < 0.55)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.55 && slideOffset < 0.6)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha2);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.6 && slideOffset < 0.65)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha3);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.65 && slideOffset < 0.7)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha4);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.7 && slideOffset < 0.75)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha5);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.75 && slideOffset < 0.8)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha6);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.8 && slideOffset < 0.85)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha7);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.85 && slideOffset < 0.9)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha8);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.9 && slideOffset < 0.95)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha9);
            grid.startAnimation(animation);
        }
        else if(slideOffset >= 0.95 && slideOffset <= 1.0)
        {
            half.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.alpha10);
            grid.startAnimation(animation);
        }
    }

    @Override
    public void onDestroy()
    {
        postViewModel.postAction();
        super.onDestroy();
    }

    public void setState(SlidingUpPanelLayout.PanelState state)
    {
        sliding.setPanelState(state);
        this.state = sliding.getPanelState();
    }

}
