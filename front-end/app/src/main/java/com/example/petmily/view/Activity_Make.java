package com.example.petmily.view;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Make extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private ActivityMakeBinding binding;
    private MakeViewModel makeViewModel;

    private Toolbar toolbar;
    private Uri uri;

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
        uriList = new ArrayList<Uri>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

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

        marker = new Marker();
        initObserver();
    }

    public void initObserver()
    {

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
                String content = binding.about.getText().toString();
                setContent(content);
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

    private void setContent(String tag){
        int i;

        ArrayList<int[]> hashTag = getSpans(tag, '#');
        ArrayList<int[]> userTag = getSpans(tag, '@');
        List<String> hash = new ArrayList<>();
        List<String> user = new ArrayList<>();
        SpannableString tagsContent = new SpannableString(tag);

        for(i = 0; i < hashTag.size(); i++){
            int[] span = hashTag.get(i);
            int hashTagStart = span[0];
            int hashTagEnd = span[1];
            hash.add(tag.substring(hashTagStart, hashTagEnd));
        }
        for(i = 0; i < userTag.size(); i++){
            int[] span = userTag.get(i);
            int hashTagStart = span[0];
            int hashTagEnd = span[1];

            user.add(tag.substring(hashTagStart+1, hashTagEnd));
        }

        for(i = 0; i < hash.size(); i++)
        {
            Log.e("해시태그 : ", hash.get(i));
        }
        int category = 1;
        makeViewModel.makePost(tag, category, latitude, longitude, uriList, 0, hash, user);
        finish();
    }

    public ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }
        return  spans;
    }
    public class HashTag extends ClickableSpan
    {
        Context context;
        TextPaint textPaint;
        public HashTag(Context context)
        {
            super();
            this.context = context;
        }
        @Override
        public void updateDrawState(TextPaint ds)
        {
            textPaint = ds;
            ds.setColor(ds.linkColor);
            ds.setARGB(255,30,144,255);
        }

        @Override
        public void onClick(View widget)
        {
            TextView tv = (TextView) widget;
            Spanned s = (Spanned) tv.getText();
            int start = s.getSpanStart(this);
            int end = s.getSpanEnd(this);
            String theWord = s.subSequence(start+1,end).toString();

        }



    }


}
