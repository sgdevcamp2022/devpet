package com.example.petmily.viewModel;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.petmily.model.data.make.Make;
import com.example.petmily.model.data.make.remote.API_Interface;
import com.example.petmily.model.data.make.remote.Post;
import com.example.petmily.model.data.make.Entity.Coord;
import com.example.petmily.model.data.make.Entity.HashTags;
import com.example.petmily.model.data.make.Entity.Location;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeViewModel extends AndroidViewModel {

    //final String URL =  "http://121.187.22.37:5000/api/app/";
    final String URL = "http://ec2-13-115-157-11.ap-northeast-1.compute.amazonaws.com:5678/api/app/";
    private MakeCallback makeCallback;

    private API_Interface makeInterface;
    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;




    private List<String> imageUri;

    private String userId;
    private String getTime;
    private String token;
    
    public MakeViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        init();
    }
    
    
    public void init()
    {
        imageUri = new ArrayList<String>();

        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        client.addInterceptor(new CustomInterceptor());

        OkHttpClient httpClient = client.build();
        makeCallback = new MakeCallback(context);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        makeInterface = retrofit.create(API_Interface.class);
    }
    
    public void makePost(String content, int category, double latitude, double longitude, List<Uri> imageUri, int groupId, List<String> hashTag, List<String> userTag)
    {
        //addImageUri(imageUri);

        int count = imageUri.size();
        List<Integer> userTagList = new ArrayList<>();
        for(int i = 0; i < userTag.size(); i++)
        {
            userTagList.add(Integer.parseInt(userTag.get(i)));
        }

        List<HashTags> hashTagList = new ArrayList<>();
        for(int i = 0; i < hashTag.size(); i++)
        {
            hashTagList.add(new HashTags(hashTag.get(i)));
        }
        Location location = new Location(category, new Coord(longitude, latitude));

        List<String> imageList = new ArrayList<String>();
        for(int i = 0; i < imageUri.size(); i++)
        {
            imageList.add("post/"+userId+"/"+getTime+(i+"")+".jpg");
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();


        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getTime = dateFormat.format(date);
        List<String> imageUrl = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < count; i++)
        {
            UploadTask uploadTask = storageReference.child("post/"+userId+"/"+getTime+(i+"")+".jpg").putFile(imageUri.get(i));
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("???????????? ?????? ?????? : ", e.toString());
                    e.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.e("???????????? ?????? ?????? : ", taskSnapshot.getMetadata().getPath());
                    storageReference.child(taskSnapshot.getMetadata().getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            queue.add(1);

                            imageUrl.add(uri.toString());
                            if(queue.size() == count)
                            {
                                Post post = new Post(content, location, userTagList, hashTagList, groupId, imageUrl);
                                Log.e("????????? ?????? ???????????? : ", post.toString());
                                restApi = makeInterface.createPost(post);
                                restApi.enqueue(makeCallback);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("???????????? ????????? ???????????? ?????? : ", e.getMessage());
                            Toast.makeText(context, "????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }

//    public void addImageUri(List<Uri> imageUri)
//    {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
//        userId = sharedPreferences.getString("userId", "");
//
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        getTime = dateFormat.format(date);
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//
//        for(int i = 0; i < imageUri.size(); i++)
//        {
//            UploadTask uploadTask = storageReference.child("post/"+userId+"/"+getTime+(i+"")+".jpg").putFile(imageUri.get(i));
//
//            //UploadTask uploadTask = storageReference.child("dog2.png").putFile(imageUri.get(i));
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.e("???????????? ?????? ?????? : ", e.toString());
//                    e.printStackTrace();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Log.e("???????????? ?????? ?????? : ", taskSnapshot.getMetadata().getPath());
//                }
//            });
//        }
//
//    }


    public String getCurrentAddress( double latitude, double longitude) {
        //????????????... GPS??? ????????? ??????
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //???????????? ??????
            return "???????????? ????????? ????????????";
        } catch (IllegalArgumentException illegalArgumentException) {
            return "????????? GPS ??????";
        }
        if (addresses == null || addresses.size() == 0) {
            return "?????? ?????????";
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
            Request re = response.raw().request();
            Log.e("???????????? : ", re.toString());
            Gson gson = new Gson();
            int responseCode = response.code();
            T body = response.body();


            try {
                Log.e("????????? ?????? ????????? : ", response.errorBody().string()+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(responseCode != 200)
            {
                
            }
        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
            Log.e("????????? ?????? ?????? : ", "");
            t.printStackTrace();
        }
    }

    public class CustomInterceptor implements okhttp3.Interceptor, HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            android.util.Log.e("MyGitHubData :", message + "");
        }


        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", token)
                    .build();

            return chain.proceed(request);
        }
    }


}
