package com.example.petmily.view;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.petmily.model.data.make.Make;
import com.example.petmily.viewModel.GpsTracker;
import com.example.petmily.viewModel.MakeViewModel;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class Activity_Make extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private ActivityMakeBinding binding;
    private MakeViewModel makeViewModel;

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
    private List<Uri> uriList;

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
        makeViewModel = new ViewModelProvider(this).get(MakeViewModel.class);
        userTag = binding.userTag;
        hashTag = binding.hashTag;
        uriList = new ArrayList<Uri>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        hashTag.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        userTag.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        initView();




        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);




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
                Dialog_Make dialog_make = new Dialog_Make(Activity_Make.this, new Dialog_Make.Dialog_MakeListener() {
                    @Override
                    public void clickBtn(String data) {
                        makeViewModel.addHashTag(data);
                    }
                });
                dialog_make.show();

            }
        });

        binding.userTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Make dialog_make = new Dialog_Make(Activity_Make.this, new Dialog_Make.Dialog_MakeListener() {
                    @Override
                    public void clickBtn(String data) {
                        makeViewModel.addUserTag(data);
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
        makeViewModel.getHashTagList().observe(this, hashTagListObserver);

        final Observer<List<Make>> userTagListObserver = new Observer<List<Make>>() {
            @Override
            public void onChanged(@Nullable final List<Make> makes) {
                Adapter_Make newAdapter = new Adapter_Make(makes);
                userTag.setAdapter(newAdapter);
            }
        };
        makeViewModel.getUserTagList().observe(this, userTagListObserver);
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
                //String content, int category, double latitude, double longitude, List<String> tag, List<String> hashTag, List<String> imageUri, int groupId
                String content = binding.about.getText().toString();
                int category = 1;


                //save
                makeViewModel.makePost(content, category, latitude, longitude, uriList, 3);
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

                    if(i.getClipData() == null){// 이미지를 하나만 선택한 경우
                        uriList = new ArrayList<Uri>();
                        uri = i.getData();
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(binding.makeImage);
                        uriList.add(uri);

                    }
                    else{      // 이미지를 여러장 선택한 경우
                        ClipData clipData = i.getClipData();
                        uri = clipData.getItemAt(0).getUri();
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(binding.makeImage);
                        for (int j = 0; j < clipData.getItemCount(); j++){
                            Uri imageUri = clipData.getItemAt(j).getUri();  // 선택한 이미지들의 uri를 가져온다.
                            uriList.add(imageUri);
                        }

                    }

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
                latitude = cameraPosition1.target.latitude;
                longitude = cameraPosition1.target.longitude;
                LatLng lng = new LatLng(latitude, longitude);
                textView.setText(makeViewModel.getCurrentAddress(lng.latitude, lng.longitude));

            }
        });


        naverMap.setCameraPosition(cameraPosition);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }



}
