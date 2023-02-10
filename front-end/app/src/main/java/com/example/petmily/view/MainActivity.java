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
import android.widget.Toast;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityMainBinding;

import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.ChatRoomViewModel;
import com.example.petmily.viewModel.ChatService;
import com.example.petmily.viewModel.ChatViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentTransaction fragmentTransaction;


    private Fragment fragment_home;
    private Fragment fragment_group;
    private Fragment fragment_profile;

    private AuthenticationViewModel authenticationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        init();

    }

    public void init()
    {
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        initObserver();
    }
    public void initObserver()
    {
        authenticationViewModel.getEventRefreshExpiration().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean) {
                    Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                    startActivity(intent);
                    finish();
                    Log.e("토큰이 없어 로그인 화면으로 이동", "");
                }
            }
        });
        checkToken();
    }

    public void checkToken()
    {
        authenticationViewModel.accessTokenCheck();
        initView();
    }
    public void initView()
    {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.make);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment_home = new Fragment_Home();
        fragment_group = new Fragment_Group();
        fragment_profile = new Fragment_Profile();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, fragment_group);
        fragmentTransaction.add(R.id.main_frame, fragment_profile);
        fragmentTransaction.add(R.id.main_frame, fragment_home).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = binding.bottomToolbar;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                fragmentTransaction = fragmentManager.beginTransaction();

                switch(item.getItemId())
                {
                    case R.id.group:
                        fragmentTransaction.hide(fragment_home);
                        fragmentTransaction.hide(fragment_profile);
                        fragmentTransaction.show(fragment_group).commit();
                        break;
                    case R.id.home:
                        fragmentTransaction.hide(fragment_group);
                        fragmentTransaction.hide(fragment_profile);
                        fragmentTransaction.show(fragment_home).commit();
                        binding.toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.profile:
                        fragmentTransaction.hide(fragment_home);
                        fragmentTransaction.hide(fragment_group);
                        fragmentTransaction.show(fragment_profile).commit();
                        binding.toolbar.setVisibility(View.GONE);
                        break;
                }
                return false;
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
            case android.R.id.home:
                //select back button
                Intent intent = new Intent(this, Activity_Login.class);
                startActivity(intent);

                //ChatRoomViewModel chatRoomViewModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);


                break;
        }
        return super.onOptionsItemSelected(item);
    }
}