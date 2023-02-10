package com.example.petmily.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityJoinBinding;
import com.example.petmily.viewModel.AuthenticationViewModel;

public class Activity_Join extends AppCompatActivity {
    private ActivityJoinBinding binding;
    private AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join);
        binding.setJoinEmail(this);
        init();
    }
    public void init()
    {
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        initView();
    }

    public void initView()
    {
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText().toString();
                String nickname = binding.nickname.getText().toString();
                String name = binding.name.getText().toString();
                String password = binding.password.getText().toString();

                authenticationViewModel.join(username, name, nickname, password);

                Intent intent = new Intent(view.getContext(), Activity_Join_Profile.class);
                startActivity(intent);
                finish();

            }
        });
        initObserver();
    }

    public void initObserver()
    {
        final Observer<Boolean> emailDuplication = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean emailDuplication) {
                if(emailDuplication)
                {
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "중복되는 이메일이 존재합니다", Toast.LENGTH_SHORT).show();
                }
            }
        };
        authenticationViewModel.getEventEmailDuplication().observe(this, emailDuplication);

        final Observer<Boolean> nicknameDuplication = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean nicknameDuplication) {
                if(nicknameDuplication)
                {
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "중복되는 닉네임이 존재합니다", Toast.LENGTH_SHORT).show();
                }

            }
        };
        authenticationViewModel.getEventNickNameDuplication().observe(this, nicknameDuplication);
    }
}