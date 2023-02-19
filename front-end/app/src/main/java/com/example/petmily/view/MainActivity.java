package com.example.petmily.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.petmily.R;

import com.example.petmily.databinding.ActivityMainBinding;
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.chat.room.local.RoomDatabase;
import com.example.petmily.model.data.chat.room.local.RoomSQL;
import com.example.petmily.model.data.chat.room.remote.Room;
import com.example.petmily.model.data.chat.room.remote.RoomAPI_Interface;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.service.ChatService;
import com.example.petmily.viewModel.service.ChatServiceThread;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

            }
        };
        authenticationViewModel.getEventRefreshExpiration().observe(this, eventRefreshExpiration);

        checkToken();
    }

    public void checkToken()
    {
        authenticationViewModel.accessTokenCheck();
        initView();
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
                //Intent i = new Intent(this, Activity_Login.class);
                Intent i = new Intent(this, Activity_Chat.class);
                startActivity(i);
                //test();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


//    String URL = "http://121.187.22.37:5555/api/chat/";
//
//    private List<RoomSQL> roomSQLList;
//    private Retrofit retrofit;
//    private RoomAPI_Interface chatInterface;
//    private String token;
//    private RoomDatabase db;
//
//    public void test()
//    {
//        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("token", Context.MODE_PRIVATE);
//        token= sharedPreferences.getString("token", "없음");
//        Log.e("토큰 확인 테스트 : ", token);
//        retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        chatInterface = retrofit.create(RoomAPI_Interface.class);
//
//        String test1 = "Bearer "+token;
//
//        Call<List<Room>> chatList = chatInterface.getMessage(token);
//        chatList.enqueue(new retrofit2.Callback<List<Room>>() {
//            @Override
//            public void onResponse(Call<List<Room>> call, retrofit2.Response<List<Room>> response) {
//                List<Room> result = null;
//                try {
//                    Log.e("채팅 테스트 : ", response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                roomSQLList = new ArrayList<>();
//                Gson gson = new Gson();
//                Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
//                result = new Gson().fromJson(response.body().toString(), listType);
//                for(int i = 0 ; i <result.size(); i++)
//                {
//                    Log.e("get 통신 테스트", result.get(i).toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Room>> call, Throwable t) {
//                Log.e("채팅 테스트 : ", "");
//                t.printStackTrace();
//            }
//        });
//    }
}