package com.example.petmily.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.petmily.R;
import com.example.petmily.databinding.JoinProfileBinding;

public class Activity_Join_Profile extends AppCompatActivity {
    private JoinProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.join_profile);
        binding.setJoinProfile(this);




    }
}
