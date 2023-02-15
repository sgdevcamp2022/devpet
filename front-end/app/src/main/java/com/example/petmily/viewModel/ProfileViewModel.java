package com.example.petmily.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.petmily.model.data.chat.room.Message;
import com.example.petmily.model.data.profile.Pet;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pet>> petList;
    public MutableLiveData<List<Pet>> getPetList() {
        if (petList == null) {
            petList = new MutableLiveData<List<Pet>>();
        }
        return petList;
    }


    private Context context;
    private List<Pet> pets;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference();
        storageRef.child("profile");


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pets = new ArrayList<>();


        /*
        ActivityResultLauncher<Intent> launcher;
        launcher = registerForActivityResult(new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // RESULT_OK일 때 실행할 코드...
                    }
                });

// launcher를 이용해서 화면 시작하기
        Intent intent = new Intent(WritePostActivity.this, GalleryActivity.class);
        launcher.launch(intent);

         */
    }
    public void petAppend(String imageUri, String name, String division, String birth, String about)
    {
        Pet pet = new Pet(name, division, birth, about, imageUri, "");
        pets.add(pet);
        petList.setValue(pets);
        Log.e("name : ", name);

    }
    public void profileSave()
    {

    }

    public void makeProfile() {
        String imageUrl;
        String nickname;
        String about;
        String birth;
        List<Pet> pet;

    }







}
