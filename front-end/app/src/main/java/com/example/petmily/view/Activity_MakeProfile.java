package com.example.petmily.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.ActivityMakeProfileBinding;
import com.example.petmily.databinding.ActivityPetAppendBinding;
import com.example.petmily.model.data.profile.Pet;
import com.example.petmily.viewModel.ProfileViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Activity_MakeProfile extends AppCompatActivity {

    private ActivityMakeProfileBinding binding;
    private ActivityPetAppendBinding activityPetAppendBinding;
    private ProfileViewModel profileViewModel;
    private Uri uri;
    private String imageUrl;
    private int year;
    private int month;
    private int day;
    private RecyclerView petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_profile);
        binding.setMakeProfile(this);

        init();
    }

    public void init()
    {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        petList = binding.petList;
        petList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartForResult.launch(intent);
            }
        });

        View.OnClickListener birthClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_DatePicker datePickerDialog = new Dialog_DatePicker(Activity_MakeProfile.this, new Dialog_DatePicker.Dialog_DateListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat old_sdf = new SimpleDateFormat("yyyyMdd");
                        SimpleDateFormat new_sdf = new SimpleDateFormat("yyyy-MM-dd");
                        year = i;
                        month = (i1+1);
                        day = i2;
                        String add = i + "" + (i1+1) + "" + i2;
                        try {
                            Date date = old_sdf.parse(add);
                            binding.time.setText(new_sdf.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        };
        binding.birth.setOnClickListener(birthClickListener);

        binding.petAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_PetAppend.class);
                petAppendResult.launch(intent);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUri = null;
                //String imageUri = uri.getPath();
                String about = binding.about.getText().toString();
                String birth = binding.time.getText().toString();

                String nickname = binding.nickname.getText().toString();
                profileViewModel.profileSave(imageUri, nickname, about, birth);
            }
        });
        initObserver();
    }

    public void initObserver()
    {
        final Observer<List<Pet>> petListObserver = new Observer<List<Pet>>() {
            @Override
            public void onChanged(@Nullable final List<Pet> pet) {
                Adapter_Pet newAdapter = new Adapter_Pet(pet);
                petList.setAdapter(newAdapter);
            }
        };
        profileViewModel.getPetList().observe(this, petListObserver);
    }


    @NonNull
    public Dialog onCreateDialog() {
     
 

//        @Override
//        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//            SimpleDateFormat old_sdf = new SimpleDateFormat("yyyyMdd");
//            SimpleDateFormat new_sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String add = i+""+i1+""+i2;
//            try {
//                Date date = old_sdf.parse(add);
//                binding.time.setText(new_sdf.format(date));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
////                binding.year.setText(i+"");
////                binding.month.setText((i1+1)+"");
////                binding.day.setText(i2+"");
//        }, year, month, day);
//        return null;
//    }

        return null;
    }



    public ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //result.getResultCode()를 통하여 결과값 확인
                if(result.getResultCode() == RESULT_OK) {
                    Intent i = result.getData();
                    uri = i.getData();

                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(binding.profileImage);

                }
                if(result.getResultCode() == RESULT_CANCELED){

                }
            }
    );

    public ActivityResultLauncher<Intent> petAppendResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    String uri = intent.getStringExtra("uri");
                    String name = intent.getStringExtra("name");
                    String division = intent.getStringExtra("division");
                    String birth = intent.getStringExtra("birth");
                    String about = intent.getStringExtra("about");


                    Log.e("name : ", name);
                    profileViewModel.petAppend(uri, name, division, birth, about);
                }

            }
    );



}
