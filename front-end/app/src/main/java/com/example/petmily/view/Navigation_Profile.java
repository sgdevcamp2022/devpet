package com.example.petmily.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.R;
import com.example.petmily.databinding.NavigationProfileBinding;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.example.petmily.viewModel.ProfileViewModel;

public class Navigation_Profile extends AppCompatActivity {
    private NavigationProfileBinding binding;

    private AuthenticationViewModel authenticationViewModel;
    private ProfileViewModel profileViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.navigation_profile);
        binding.setProfile(this);
        Log.e("asdsad", "asdsad");
        init();


    }
    public void init()
    {
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationViewModel.logout();
            }
        });

        binding.profileReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_MakeProfile.class);
                intent.putExtra("replace", true);
                Log.e("asdsad", "asdsad");
                startActivity(intent);
            }
        });
    }
}
