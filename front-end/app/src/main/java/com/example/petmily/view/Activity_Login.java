package com.example.petmily.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityLoginBinding;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.service.ChatService;


public class Activity_Login extends AppCompatActivity {

    private AuthenticationViewModel authenticationViewModel;
    private ActivityLoginBinding binding;
    private Intent intent;
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setAuthJoin(this);
        init();
    }
    public void init()
    {
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        service = new Intent(Activity_Login.this, ChatService.class);
        initView();
    }
    public void initView()
    {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                authenticationViewModel.login(email, password);
            }
        });

        binding.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), Activity_Join.class);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationViewModel.logout();
                stopService(service);
            }
        });

        initObserver();
    }
    public void initObserver()
    {
        final Observer<Boolean> eventLoginExiration = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if(!aBoolean) {
                    Toast.makeText(getApplicationContext(), "사용자 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //startService(service);
                }

            }
        };
        authenticationViewModel.getEventLoginExpiration().observe(this, eventLoginExiration);


        final Observer<Boolean> eventRefreshObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if(!aBoolean) {
                    Log.e("리프레시 만료 : ", "");
                }
                else
                {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //startService(service);
                }

            }
        };
        authenticationViewModel.getEventRefreshExpiration().observe(this, eventRefreshObserver);
    }
}
