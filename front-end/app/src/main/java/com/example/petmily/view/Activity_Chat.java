package com.example.petmily.view;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityChatBinding;

public class Activity_Chat extends AppCompatActivity {

    private ActivityChatBinding binding;
    private Fragment_Alarm fragment_alarm;
    private Fragment_Message fragment_message;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setChatList(this);

        Toolbar toolbar = binding.backToolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment_alarm = new Fragment_Alarm();
        fragment_message = new Fragment_Message();


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.chat_frame, fragment_alarm).commitAllowingStateLoss();


        Button b1 = binding.alarm;
        Button b2 = binding.message;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.chat_frame, fragment_alarm).commitAllowingStateLoss();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.chat_frame, fragment_message).commitAllowingStateLoss();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //select back button
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
