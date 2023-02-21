package com.example.petmily.viewModel;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.petmily.model.data.make.Make;
import com.example.petmily.model.data.make.remote.API_Interface;
import com.example.petmily.model.data.make.remote.Post;
import com.example.petmily.model.data.post.Entity.Coord;
import com.example.petmily.model.data.post.Entity.Location;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeViewModel extends AndroidViewModel {

    final String URL =  "http://10.0.2.2:1367/api/app/";
    private MakeCallback makeCallback;

    private API_Interface makeInterface;
    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;




    
    private MutableLiveData<List<Make>> userTagList;
    public MutableLiveData<List<Make>> getUserTagList() {
        if (userTagList == null) {
            userTagList = new MutableLiveData<List<Make>>();
        }
        return userTagList;
    }

    private MutableLiveData<List<Make>> hashTagList;
    public MutableLiveData<List<Make>> getHashTagList() {
        if (hashTagList == null) {
            hashTagList = new MutableLiveData<List<Make>>();
        }
        return hashTagList;
    }


    
    private List<Make> userTag;
    private List<Make> hashTag;
    private List<String> imageUri;

    private String email;
    private String getTime;

    
    public MakeViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        init();
    }
    
    
    public void init()
    {
        hashTag = new ArrayList<Make>();
        userTag = new ArrayList<Make>();
        imageUri = new ArrayList<String>();

        makeCallback = new MakeCallback(context);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        makeInterface = retrofit.create(API_Interface.class);
    }
    
    public void makePost(String content, int category, double latitude, double longitude, List<Uri> imageUri, int groupId)
    {
        addImageUri(imageUri);

        List<String> userTagList = new ArrayList<String>();
        for(int i = 0; i < userTag.size(); i++)
        {
            userTagList.add(userTag.get(i).getMake());
        }

        List<String> hashTagList = new ArrayList<String>();
        for(int i = 0; i < hashTag.size(); i++)
        {
            hashTagList.add(hashTag.get(i).getMake());
        }
        Location location = new Location(category, new Coord(latitude, longitude));

        List<String> imageList = new ArrayList<String>();
        for(int i = 0; i < imageUri.size(); i++)
        {
            imageList.add("post/"+email+"/"+getTime+(i+"")+".jpg");
            //imageList.add("dog2.png");
        }


        Post post = new Post(content, category, location, userTagList, hashTagList, imageList, 0);

        restApi = makeInterface.createPost(post);
        restApi.enqueue(makeCallback);

    }

    public void addImageUri(List<Uri> imageUri)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getTime = dateFormat.format(date);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        for(int i = 0; i < imageUri.size(); i++)
        {
            UploadTask uploadTask = storageReference.child("post/"+email+"/"+getTime+(i+"")+".jpg").putFile(imageUri.get(i));

            //UploadTask uploadTask = storageReference.child("dog2.png").putFile(imageUri.get(i));
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("실패 메시지 : ", "");
                    e.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.e("주소 스트링으로 출력 : ", taskSnapshot.getMetadata().getPath());
                }
            });
        }

    }

    public void addHashTag(String tag)
    {
        hashTag.add(new Make(tag));
        hashTagList.setValue(hashTag);

    }
    public void addUserTag(String tag)
    {
        userTag.add(new Make(tag));
        userTagList.setValue(userTag);
    }

    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }


    public class MakeCallback<T> implements retrofit2.Callback<T> {
        /*
        final int SUCCESS               = 200;

        final int INVALID_PARAMETER     = 400;
        final int NEED_LOGIN            = 401;
        final int UNAUTHORIZED          = 403;
        final int NOT_FOUND             = 404;

        final int INTERNAL_SERVER_ERROR = 500;

         */

        Context context;

        public MakeCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(retrofit2.Call<T> call, retrofit2.Response<T> response) {

            Gson gson = new Gson();
            int responseCode = response.code();//네트워크 탐지할 때 사용 코드
            T body = response.body();


            try {
                Log.e("메이크 통신 성공 : ", response.errorBody().string()+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(responseCode != 200)
            {



            }
        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
            Log.e("메이크 통신 실패 : ", "");
            t.printStackTrace();
        }
    }




}
