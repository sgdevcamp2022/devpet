package com.example.petmily.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityJoinBinding;
import com.example.petmily.model.Join;
import com.example.petmily.viewModel.LoginViewModel;

public class Activity_Join extends AppCompatActivity {
    private ActivityJoinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join);
        binding.setJoin(this);

        String intent_data;
        intent_data = getIntent().getStringExtra("username");
        if(intent_data != null)
        {
            binding.username.setText(getIntent().getStringExtra("username"));
            binding.name.setText(getIntent().getStringExtra("name"));
            binding.age.setText(Integer.parseInt(getIntent().getStringExtra("age")+""));
            binding.nickname.setText(Integer.parseInt(getIntent().getStringExtra("password"))+"");
        }

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText().toString();
                String nickname = binding.nickname.getText().toString();
                String name = binding.name.getText().toString();
                String password = binding.password.getText().toString();
                int age = Integer.parseInt(binding.age.getText().toString());
                String gender = binding.gender.getText().toString();
                int phone = Integer.parseInt(binding.phone.getText().toString());
                Join join = new Join(username, nickname, name);
                Join test_join = new Join("계정", "채채현수", "채현수");

                loginViewModel.join(test_join);

                Intent intent = new Intent(view.getContext(), Activity_Join_Profile.class);

                //

                startActivity(intent);
                finish();

            }
        });



    }

}
