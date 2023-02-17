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

import java.util.Calendar;

public class Activity_PetAppend extends AppCompatActivity {

    private ActivityPetAppendBinding binding;
    private ProfileViewModel profileViewModel;
    private Uri uri;
    private String imageUrl;
    private String year;
    private String month;
    private String day;


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

        View.OnClickListener birthClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onCreateDialog();
                dialog.show();
            }
        };
        binding.birth.setOnClickListener(birthClickListener);


        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.name.getText().toString();
                String division = binding.division.getText().toString();
                String birth =
                        binding.year.getText()+"-"+
                        binding.month.getText()+"-"+
                        binding.day.getText();
                String about = binding.about.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("uri", uri.toString());
                intent.putExtra("name", name);
                intent.putExtra("division", division);
                intent.putExtra("birth", birth);
                intent.putExtra("about", about);

                setResult(RESULT_OK, intent);

                finish();

            }
        });
        /*
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/profile_img");

        if(!file.isDirectory())
            file.mkdir();

         */
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
                binding.year.setText(i + "년");
                binding.month.setText((i1 + 1) + "월");
                binding.day.setText(i2 + "일");
            }
        }, year, month, day);

        return datePickerDialog;
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
