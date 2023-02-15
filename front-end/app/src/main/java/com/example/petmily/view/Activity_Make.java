package com.example.petmily.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.ActivityMakeBinding;
import com.example.petmily.model.data.profile.make.Make;
import com.example.petmily.viewModel.GpsTracker;
import com.example.petmily.viewModel.PostViewModel;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Activity_Make extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private ActivityMakeBinding binding;
    private PostViewModel postViewModel;

    private Toolbar toolbar;
    private Uri uri;
    private RecyclerView userTag;
    private RecyclerView hashTag;

    private MapView mapView;
    private static NaverMap naverMap;
    private Marker marker;

    private GpsTracker gpsTracker;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make);
        binding.setMake(this);


        gpsTracker = new GpsTracker(getApplicationContext());

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();


        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        init();
    }

    public void init()
    {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        userTag = binding.userTag;
        hashTag = binding.hashTag;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        hashTag.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        userTag.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        initView();

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        mStartForResult.launch(intent);
    }

    public void initView()
    {
        toolbar = binding.backToolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        binding.makeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureReplace.launch(intent);
            }
        });

        binding.hashTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("zxczxv", "zxczxc");
                Dialog_Make dialog_make = new Dialog_Make(Activity_Make.this, new Dialog_Make.Dialog_MakeListener() {
                    @Override
                    public void clickBtn(String data) {
                        postViewModel.addHashTag(data);
                    }
                });
                dialog_make.show();

            }
        });


        binding.userTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("asd", "asd");
                Dialog_Make dialog_make = new Dialog_Make(Activity_Make.this, new Dialog_Make.Dialog_MakeListener() {
                    @Override
                    public void clickBtn(String data) {
                        postViewModel.addUserTag(data);
                    }
                });
                dialog_make.show();
            }
        });

        marker = new Marker();
        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<Make>> hashTagListObserver = new Observer<List<Make>>() {
            @Override
            public void onChanged(@Nullable final List<Make> makes) {
                Adapter_Make newAdapter = new Adapter_Make(makes);
                hashTag.setAdapter(newAdapter);
            }
        };
        postViewModel.getHashTagList().observe(this, hashTagListObserver);

        final Observer<List<Make>> userTagListObserver = new Observer<List<Make>>() {
            @Override
            public void onChanged(@Nullable final List<Make> makes) {
                Adapter_Make newAdapter = new Adapter_Make(makes);
                userTag.setAdapter(newAdapter);
            }
        };
        postViewModel.getUserTagList().observe(this, userTagListObserver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.make_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                //save
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //result.getResultCode()를 통하여 결과값 확인
                if(result.getResultCode() == RESULT_OK) {
                    Intent i = result.getData();
                    uri = i.getData();

                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(binding.makeImage);

                }
                if(result.getResultCode() == RESULT_CANCELED){
                    finish();
                }
            }
    );

    public ActivityResultLauncher<Intent> pictureReplace = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //result.getResultCode()를 통하여 결과값 확인
                if(result.getResultCode() == RESULT_OK) {
                    Intent i = result.getData();
                    uri = i.getData();

                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(binding.makeImage);

                }
                if(result.getResultCode() == RESULT_CANCELED){
                }
            }
    );

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;

        TextView textView = binding.address;
        LatLng latLng = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition(latLng, 18);
        marker.setPosition(latLng);
        marker.setMap(naverMap);

        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean b) {
                CameraPosition cameraPosition1 = naverMap.getCameraPosition();
                LatLng lng = new LatLng(cameraPosition1.target.latitude, cameraPosition1.target.longitude);

                marker.setPosition(lng);
                marker.setMap(naverMap);
            }
        });

        naverMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition1 = naverMap.getCameraPosition();
                LatLng lng = new LatLng(cameraPosition1.target.latitude, cameraPosition1.target.longitude);
                textView.setText(getCurrentAddress(lng.latitude, lng.longitude));
            }
        });


        naverMap.setCameraPosition(cameraPosition);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getApplicationContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getApplicationContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getApplicationContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

}
