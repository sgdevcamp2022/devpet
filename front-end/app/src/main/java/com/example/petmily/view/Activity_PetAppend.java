package com.example.petmily.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.petmily.R;
import com.example.petmily.databinding.ActivityMakeBinding;
import com.example.petmily.databinding.ActivityMakeProfileBinding;
import com.example.petmily.databinding.ActivityPetAppendBinding;
import com.example.petmily.viewModel.ProfileViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activity_PetAppend extends AppCompatActivity {

    private ActivityPetAppendBinding binding;
    private ProfileViewModel profileViewModel;
    private Uri uri;
    private String imageUrl;
    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_append);
        binding.setPetAppend(this);



        init();


    }

    public void init() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getPetList();

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        binding.petImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartForResult.launch(intent);
            }
        });
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        View.OnClickListener birthClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_DatePicker datePickerDialog = new Dialog_DatePicker(Activity_PetAppend.this, new Dialog_DatePicker.Dialog_DateListener() {
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

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.name.getText().toString();
                String division = binding.division.getText().toString();
                String birth = binding.time.getText().toString();
                String about = binding.about.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("uri", "");
                intent.putExtra("name", name);
                intent.putExtra("division", division);
                intent.putExtra("birth", birth);
                intent.putExtra("about", about);

                setResult(RESULT_OK, intent);

                finish();

            }
        });
    }
    public ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //result.getResultCode()를 통하여 결과값 확인
                if (result.getResultCode() == RESULT_OK) {
                    Intent i = result.getData();
                    uri = i.getData();

                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(binding.petImage);


                    Log.e("URL 주소 출력 : ", uri.toString());
                    Log.e("URL 주소 스트링으로 출력 : ", uri.getPath());
                }
                if (result.getResultCode() == RESULT_CANCELED) {
                    //ToDo
                    Log.e("URL 주소 : ", "실패패");
                }
            }
    );


}
