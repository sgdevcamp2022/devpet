package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.petmily.model.data.profile.Pet;
import com.example.petmily.model.data.profile.remote.API_Interface;
import com.example.petmily.model.data.profile.remote.Profile;
import com.example.petmily.model.data.profile.remote.SuccessFollow;
import com.example.petmily.model.data.profile.remote.SuccessFollower;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileViewModel extends AndroidViewModel {

    final String URL = "https://121.187.37.22:5555/api/app/";
    private FirebaseStorage firebaseStorage;
    private StorageReference storageRef;

    //private ProfileDatabase db;
    private ProfileCallback profileCallback;

    private API_Interface profileInterface;
    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;

    private MutableLiveData<List<Pet>> petList;
    public MutableLiveData<List<Pet>> getPetList() {
        if (petList == null) {
            petList = new MutableLiveData<List<Pet>>();
        }
        return petList;
    }

    private MutableLiveData<Profile> profile;
    public MutableLiveData<Profile> getProfile() {
        if (profile == null) {
            profile = new MutableLiveData<Profile>();
        }
        return profile;
    }

    private MutableLiveData<SuccessFollow> followList;
    public MutableLiveData<SuccessFollow> getFollow() {
        if (followList == null) {
            followList = new MutableLiveData<SuccessFollow>();
        }
        return followList;
    }

    private MutableLiveData<SuccessFollower> followerList;
    public MutableLiveData<SuccessFollower> getFollower() {
        if (followerList == null) {
            followerList = new MutableLiveData<SuccessFollower>();
        }
        return followerList;
    }

    private List<Pet> pets;
    private String email;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference();
        storageRef.child("profile");




    }
    public void init()
    {
        pets = new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        //db = ProfileDatabase.getInstance(context);
        profileCallback = new ProfileCallback(context);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        profileInterface = retrofit.create(API_Interface.class);

    }


    public void profileImport()
    {
//        restApi = profileInterface.getProfile(email);
//        restApi.enqueue(profileCallback);



        //테스트 코드
        Profile p = new Profile("", "닉네임", "소개", "생일", pets);
        profile.setValue(p);

        List<Profile> test =  new ArrayList<Profile>();
        test.add(p);
        SuccessFollow successFollow = new SuccessFollow("", true,test);
        followList.setValue(successFollow);

        SuccessFollower successFollower = new SuccessFollower("", true,test);
        followerList.setValue(successFollower);

    }

    public void petAppend(String imageUri, String name, String division, String birth, String about)
    {
        Pet pet = new Pet(name, division, birth, about, imageUri, "");
        pets.add(pet);
        petList.setValue(pets);

    }
    public void profileSave(String imageUri, String name, String about, String birth)
    {
        Profile profile = new Profile(imageUri, name, about, birth, pets);
        restApi = profileInterface.saveProfile(profile);
        restApi.enqueue(profileCallback);

    }


    public class ProfileCallback<T> implements retrofit2.Callback<T> {
        private FirebaseStorage storage;
        private StorageReference storageReference;

        final int SUCCESS               = 200;

        final int INVALID_PARAMETER     = 400;
        final int NEED_LOGIN            = 401;
        final int UNAUTHORIZED          = 403;
        final int NOT_FOUND             = 404;

        final int INTERNAL_SERVER_ERROR = 500;

        Context context;

        public ProfileCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(retrofit2.Call<T> call, retrofit2.Response<T> response) {

            Gson gson = new Gson();
            int responseCode = response.code();//네트워크 탐지할 때 사용 코드
            T body = response.body();

            if(responseCode == SUCCESS)
            {
                if(body instanceof Profile) {
                    firebaseStorage = FirebaseStorage.getInstance();
                    storageRef = firebaseStorage.getReference();
                    Profile result = (Profile) body;

                    storageReference.child(result.getImageUri()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            result.setImageUri(uri.getPath());
                            profile.setValue(result);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.e("파일 불러오기 실패 : ", e.getMessage());
                        }
                    });
                }
                if(body instanceof SuccessFollow)
                {
                    SuccessFollow successFollow = (SuccessFollow) body;

                    followList.setValue(successFollow);
                }
                if(body instanceof SuccessFollower)
                {
                    SuccessFollower successFollower = (SuccessFollower) body;

                    followerList.setValue(successFollower);
                }
            }
        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
        }
    }





}
