package com.example.petmily.view;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentHomeBinding;
import com.example.petmily.model.Place;
import com.example.petmily.model.Post_half;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostHalf;
import com.example.petmily.model.data.post.remote.Post;
import com.example.petmily.viewModel.GpsTracker;
import com.example.petmily.viewModel.PostViewModel;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Fragment_Home extends Fragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private FragmentHomeBinding binding;
    private MapView mapView;
    private static NaverMap naverMap;
    private FusedLocationSource locationSource;

    private FragmentManager fragmentManager;

    private RecyclerView halfView;
    private RecyclerView grid;
    private RecyclerView place;

    private LinearLayout half;

    private PostViewModel postViewModel;

    private LinearLayoutManager linearLayoutManager;

    private LocationManager locationManager;

    private GpsTracker gpsTracker;
    double latitude;
    double longitude;

    private List<LatLng> latLngs;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        permission();

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        gpsTracker = new GpsTracker(context);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();


        /*
        NaverMapSdk.getInstance(context).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("ztkfe47y3q"));

         */


        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        init();

        return view;
    }

    public void init()
    {

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.init();



        halfView = binding.postHalfFrame;
        grid = binding.postGridFrame;
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        halfView.setLayoutManager(linearLayoutManager);


        fragmentManager = getParentFragmentManager();




        grid.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));





        half = binding.postHalfLayout;


        place = binding.placeList;
        place.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<Place> test = new ArrayList<Place>();
        test.add(new Place("장소1"));
        test.add(new Place("장소2"));
        test.add(new Place("장소3"));
        test.add(new Place("장소4"));
        test.add(new Place("장소5"));
        Adapter_Place adapterPlace = new Adapter_Place(test);
        place.setAdapter(adapterPlace);








        SlidingUpPanelLayout sliding = binding.slidingLayout;
        sliding.setClipToOutline(true);
        sliding.setTouchEnabled(true);
        sliding.setAnchorPoint(0.4F);
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
            }
        });
        initObserver();
    }

    public void initObserver()
    {

        final Observer<List<PostGrid>> postGridObserver  = new Observer<List<PostGrid>>() {
            @Override
            public void onChanged(@Nullable final List<PostGrid> postGrids) {
                Adapter_PostGrid newAdapter = new Adapter_PostGrid(postGrids);
                grid.setAdapter(newAdapter);
            }
        };
        postViewModel.getPostGrid().observe(getViewLifecycleOwner(), postGridObserver);

        final Observer<List<PostHalf>> postHalfObserver  = new Observer<List<PostHalf>>() {
            @Override
            public void onChanged(@Nullable final List<PostHalf> postHalfList) {
                Adapter_PostHalf newAdapter = new Adapter_PostHalf(postHalfList);
                halfView.setAdapter(newAdapter);
            }
        };
        postViewModel.getPostHalf().observe(getViewLifecycleOwner(), postHalfObserver);


        final Observer<CameraPosition> cameraPositionObserver  = new Observer<CameraPosition>() {
            @Override
            public void onChanged(@Nullable final CameraPosition cameraPosition) {
                CameraUpdate cameraUpdate = CameraUpdate.toCameraPosition(cameraPosition).animate(CameraAnimation.Easing);
                naverMap.moveCamera(cameraUpdate);
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        };
        postViewModel.getCameraPosition().observe(getViewLifecycleOwner(), cameraPositionObserver);

        final Observer<List<Marker>> markerObserver  = new Observer<List<Marker>>() {
            @Override
            public void onChanged(@Nullable final List<Marker> markers) {
                for(Marker marker : markers)
                {
                    marker.setMap(naverMap);
                }
            }
        };
        postViewModel.getMarkerList().observe(getViewLifecycleOwner(), markerObserver);




        halfView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                postViewModel.moveMap(linearLayoutManager.findFirstVisibleItemPosition());
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
            }
        });

        postViewModel.postHalf();
        postViewModel.postGrid();

        grid.setVisibility(View.INVISIBLE);



    }






    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;

        CameraPosition cameraPosition = new CameraPosition(new LatLng(latitude, longitude),18);
        naverMap.setCameraPosition(cameraPosition);
        //naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        naverMap.setMapType(NaverMap.MapType.Basic);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(false);
        naverMap.addOnLoadListener(new NaverMap.OnLoadListener() {
            @Override
            public void onLoad() {
                postViewModel.setMarker();
            }
        });



    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }



    public void permission()
    {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.e("퍼미션 동의 : ", "true");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Log.e("퍼미션 동의 : ", "false");
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("서비스를 사용하기 위해서는 위치 권한을 등록해야 합니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
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


}
