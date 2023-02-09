package com.example.petmily.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityJoinBinding;
import com.example.petmily.model.data.auth.remote.JoinEmail;
import com.example.petmily.viewModel.AuthenticationViewModel;

public class Activity_Join extends AppCompatActivity {
    private ActivityJoinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join);
        binding.setJoinEmail(this);

        String provider;
        provider = getIntent().getStringExtra("provider");
        if(provider.equals("kakao"))
        {
            binding.username.setText(getIntent().getStringExtra("username"));
            binding.name.setText(getIntent().getStringExtra("name"));
            //binding.age.setText(getIntent().getStringExtra("age"));
            binding.password.setText(getIntent().getStringExtra("password"));
        }

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText().toString();
                String nickname = binding.nickname.getText().toString();
                String name = binding.name.getText().toString();
                String password = binding.password.getText().toString();
                String age = binding.age.getText().toString();
                String gender = binding.gender.getText().toString();
                String phone = binding.phone.getText().toString();

                JoinEmail kakaoAuthJoinEmail = new JoinEmail(username, name, password);
                JoinEmail kakaoAuthJoinEmail2 = new JoinEmail(username, nickname, age, gender, phone);


                JoinEmail emailAuthJoinEmail = new JoinEmail(username, nickname, name, password, age, gender, phone, "");


                JoinEmail test__joinEmail = new JoinEmail("계정", "채채현수", "채현수");


                //authenticationViewModel.join(test_join);

                Intent intent = new Intent(view.getContext(), Activity_Join_Profile.class);
                startActivity(intent);
                finish();

            }
        });



    }

}
