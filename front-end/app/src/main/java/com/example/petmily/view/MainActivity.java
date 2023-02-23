package com.example.petmily.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.example.petmily.R;

import com.example.petmily.databinding.ActivityMainBinding;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.service.ChatService;
import com.example.petmily.viewModel.service.ChatWorker;
import com.example.petmily.viewModel.service.UndeadService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentTransaction fragmentTransaction;

    private Fragment fragment_home;
    private Fragment fragment_group;
    private Fragment fragment_profile;
    private Fragment_Home fragment;

    private AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        String roomId = getIntent().getDataString();
        if(roomId != null)
        {
            Intent intent = new Intent(getApplicationContext(), Activity_Chat.class);
            intent.putExtra("roomId", roomId);
            startActivity(intent);
        }
        init();
        /*
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("asdsad", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast

                        Log.d("asdsad", token);
                    }
                });
         */
    }

    public void init()
    {
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        authenticationViewModel.init();
        initObserver();
    }
    public void initObserver()
    {
        final Observer<Boolean> eventRefreshExpiration = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if(!aBoolean) {
                    Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                    startActivity(intent);
                    finish();
                    Log.e("토큰이 없어 로그인 화면으로 이동", "");

                }
                else
                {
                    initView();
                }

            }
        };
        authenticationViewModel.getEventRefreshExpiration().observe(this, eventRefreshExpiration);

        checkToken();
    }

    public void checkToken()
    {
        authenticationViewModel.accessTokenCheck();
    }
    public void initView() {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        binding.make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Make.class);
                startActivity(intent);
            }
        });
        fragment = new Fragment_Home();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment_home = new Fragment_Home();
        fragment_group = new Fragment_Search();
        fragment_profile = new Fragment_Profile();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, fragment_group);
        fragmentTransaction.add(R.id.main_frame, fragment_profile);
        fragmentTransaction.add(R.id.main_frame, fragment_home).commitAllowingStateLoss();
        binding.home.setImageResource(R.drawable.home_touch);

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragment_home);
                fragmentTransaction.hide(fragment_profile);
                fragmentTransaction.show(fragment_group).commit();
                binding.search.setImageResource(R.drawable.search_touch);
                binding.home.setImageResource(R.drawable.home);
                binding.user.setImageResource(R.drawable.user);
            }
        });
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragment_group);
                fragmentTransaction.hide(fragment_profile);
                fragmentTransaction.show(fragment_home).commit();
                binding.toolbar.setVisibility(View.VISIBLE);
                binding.search.setImageResource(R.drawable.search);
                binding.home.setImageResource(R.drawable.home_touch);
                binding.user.setImageResource(R.drawable.user);
            }
        });

        binding.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragment_home);
                fragmentTransaction.hide(fragment_group);
                fragmentTransaction.show(fragment_profile).commit();
                binding.toolbar.setVisibility(View.GONE);
                binding.search.setImageResource(R.drawable.search);
                binding.home.setImageResource(R.drawable.home);
                binding.user.setImageResource(R.drawable.user_touch);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dm:
                Intent i = new Intent(this, Activity_Chat.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {

        if(fragment.state== SlidingUpPanelLayout.PanelState.EXPANDED)
        {
            fragment.setState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }
//        else if(fragment.state==SlidingUpPanelLayout.PanelState.ANCHORED)
//        {
//            fragment.setState(SlidingUpPanelLayout.PanelState.HIDDEN);
//        }
        else {
            super.onBackPressed();
        }
    }
}