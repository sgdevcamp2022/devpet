package com.example.petmily.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityMainBinding;

import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.ChatRoomViewModel;
import com.example.petmily.viewModel.ChatService;
import com.example.petmily.viewModel.ChatViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FragmentTransaction fragmentTransaction;


    Fragment fragment_home;
    Fragment fragment_group;
    Fragment fragment_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        initView();
        checkToken();

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);


        String acstoken = "asdsad";
        //if(acstoken.getacs == true) {}
        //else if(acstoken.getacs == false){
            String refresh = "get";
            acstoken = "새로받은 값";

            //if(refresh == false){}

                //로그아웃절차 후 토큰
                refresh = "";
                //에러코드 body:4004 : 500 (리프레쉬 null값)
                //에러코드 4005 : 500



        //}

        /*
            Authorization : Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzUzNDc3NDQsInVzZXJfbmFtZSI6ImFjczEyMUBuYXZlci5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMjZlZTRkYzAtNzlmOS00YzEzLTkyZjEtM2UxYmJlZjQ2ZGI3IiwiY2xpZW50X2lkIjoiZGV2Iiwic2NvcGUiOlsidHJ1c3QiXX0.T2alTimtzNP8FqJNwn6T5zDLBO6gR0Iu9-RSeoPk46g
            String token = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzUzNDc3NDQsInVzZXJfbmFtZSI6ImFjczEyMUBuYXZlci5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMjZlZTRkYzAtNzlmOS00YzEzLTkyZjEtM2UxYmJlZjQ2ZGI3IiwiY2xpZW50X2lkIjoiZGV2Iiwic2NvcGUiOlsidHJ1c3QiXX0.T2alTimtzNP8FqJNwn6T5zDLBO6gR0Iu9-RSeoPk46g


         */





        //PlaceViewModel placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);




       // LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //LoginViewModel loginViewModel = new ViewModelProvider(this, new LoginViewModel(MainActivity.getActivity().getApplication())).get(Activity_Login.class);

        Intent intent = new Intent(MainActivity.this, ChatService.class);
        //startService(intent);





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
                //select logout item
                Intent i = new Intent(this, Activity_Chat.class);
                startActivity(i);
                break;
            case android.R.id.home:
                //select back button
                Intent intent = new Intent(this, Activity_Login.class);
                //startActivity(intent);

                //ChatRoomViewModel chatRoomViewModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);


                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void checkToken()
    {

    }

}