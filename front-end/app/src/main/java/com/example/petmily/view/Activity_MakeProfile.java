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
import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.profile.Pet;
import com.example.petmily.viewModel.ProfileViewModel;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class Activity_MakeProfile extends AppCompatActivity {

    private ActivityMakeProfileBinding binding;
    private ActivityPetAppendBinding activityPetAppendBinding;
    private ProfileViewModel profileViewModel;
    private Uri uri;
    private String imageUrl;
    private String year;
    private String month;
    private String day;
    private RecyclerView petList;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_profile);
        binding.setMakeProfile(this);

        nickname = getIntent().getStringExtra("nickname");

        init();
    }

    public void init()
    {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        petList = binding.petList;
        petList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

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
                Dialog dialog = onCreateDialog();
                dialog.show();
            }
        };
        binding.birth.setOnClickListener(birthClickListener);



        binding.petAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Dialog dialog01;
                dialog01 = new Dialog(Activity_MakeProfile.this);
                dialog01.setContentView(R.layout.activity_pet_append);
                dialog01.show();

                 */

                Intent intent = new Intent(getApplicationContext(), Activity_PetAppend.class);
                petAppendResult.launch(intent);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUri = uri.toString();
                String about = binding.about.getText().toString();
                String birth = binding.year.getText()+ "-" + binding.month.getText()+ "-" + binding.day.getText();

                profileViewModel.profileSave(imageUri, nickname, about, birth);
            }
        });



        /*
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/profile_img");

        if(!file.isDirectory())
            file.mkdir();

         */
        initObserver();

    }



    @NonNull
    public Dialog onCreateDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.year.setText(i+"년");
                binding.month.setText((i1+1)+"월");
                binding.day.setText(i2+"일");
            }
        }, year, month, day);

        return datePickerDialog;
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

}
