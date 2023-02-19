package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.petmily.model.data.profile.remote.ChatRoomMake;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileViewModel extends AndroidViewModel {

    final String URL = "http://121.187.22.37:5555/api/app/";
    final String CHATURL = "http://121.187.22.37:5555/api/chat/";

    private FirebaseStorage firebaseStorage;
    private StorageReference storageRef;

    //private ProfileDatabase db;
    private ProfileCallback profileCallback;

    private API_Interface profileInterface;
    private API_Interface chatRoomInterface;
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

    private MutableLiveData<String> roomIdLive;
    public MutableLiveData<String> getRoomId() {
        if (roomIdLive == null) {
            roomIdLive = new MutableLiveData<String>();
        }
        return roomIdLive;
    }

    private SingleLiveEvent<Boolean> followEvent;
    public SingleLiveEvent<Boolean> getFollowEvent() {
        if (followEvent == null) {
            followEvent = new SingleLiveEvent<Boolean>();
        }
        return followEvent;
    }

    private List<Pet> pets;
    private String token;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

        init();
    }
    public void init()
    {
        pets = new ArrayList<>();
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference();
        storageRef.child("profile");

        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        //db = ProfileDatabase.getInstance(context);
        profileCallback = new ProfileCallback(context);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", token)
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient httpClient = client.build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        profileInterface = retrofit.create(API_Interface.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(CHATURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        chatRoomInterface = retrofit.create(API_Interface.class);

    }

    public void profileMyImport()
    {
        restApi = profileInterface.getMyProfile();
        restApi.enqueue(profileCallback);



        //테스트 코드
//        Profile p = new Profile("", "닉네임", "소개", "생일", pets);
//        profile.setValue(p);
//
//        List<Profile> test =  new ArrayList<Profile>();
//        test.add(p);
//        SuccessFollow successFollow = new SuccessFollow("", true,test);
//        followList.setValue(successFollow);
//
//        SuccessFollower successFollower = new SuccessFollower("", true,test);
//        followerList.setValue(successFollower);
    }

    public void profileImport(String userId)
    {
        restApi = profileInterface.getProfile(userId);
        restApi.enqueue(profileCallback);


//        //테스트 코드
//        Profile p = new Profile("", "닉네임", "소개", "생일", pets);
//        profile.setValue(p);
//
//        List<Profile> test =  new ArrayList<Profile>();
//        test.add(p);
//        SuccessFollow successFollow = new SuccessFollow("", true,test);
//        followList.setValue(successFollow);
//
//        SuccessFollower successFollower = new SuccessFollower("", true,test);
//        followerList.setValue(successFollower);

    }
    public void petAppend(String imageUri, String name, String division, String birth, String about)
    {
        Pet pet = new Pet(name, division, birth, about, imageUri, "");
        pets.add(pet);
        petList.setValue(pets);

    }
    public void profileSave(String imageUri, String nickname, String about, String birth)
    {
        Profile profile = new Profile(nickname, about, birth, pets);
        //Profile profile = new Profile(imageUri, name, about, birth, pets);
        restApi = profileInterface.saveProfile(profile);
        restApi.enqueue(profileCallback);

    }

    //프로필로 넘어갈 예졍
    public void createChatRoom(String userId)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("userId", "");
        List<String> list = new ArrayList<String>();
        if(!myId.equals(""))
        {
            list.add(myId);
            list.add(userId);
        }

        restApi =  chatRoomInterface.createRoom(list);
        restApi.enqueue(profileCallback);

        //list.add("1");//내 이메일
        //list.add("2");//상대 이메일
//        Call<ChatRoomMake> testCallback = chatRoomInterface.createRoom(list);
//        testCallback.enqueue(new retrofit2.Callback<ChatRoomMake>(){
//
//            @Override
//            public void onResponse(Call<ChatRoomMake> call, Response<ChatRoomMake> response) {
//
//                ChatRoomMake result = response.body();
//                roomIdLive.setValue(result.getRoomId());
//
//            }
//            @Override
//            public void onFailure(Call<ChatRoomMake> call, Throwable t) {
//                Log.e("방 생성 실패 : ",t.getMessage());
//            }
//        });

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

            if(responseCode == SUCCESS) {
                if (body instanceof Profile) {
                    firebaseStorage = FirebaseStorage.getInstance();
                    storageRef = firebaseStorage.getReference();
                    Profile result = (Profile) body;

                    //팔로잉 여부 반환
//                    followEvent.setValue(result.getFollow());

                    //프로필 이미지 메소드
//                    storageReference.child(result.getImageUri()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            result.setImageUri(uri.getPath());
//                            profile.setValue(result);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            //Log.e("파일 불러오기 실패 : ", e.getMessage());
//                        }
//                    });
                }
                //현재 팔로우 조회 안됨
//                if(body instanceof SuccessFollow)
//                {
//                    SuccessFollow successFollow = (SuccessFollow) body;
//
//                    followList.setValue(successFollow);
//                }
//                if(body instanceof SuccessFollower)
//                {
//                    SuccessFollower successFollower = (SuccessFollower) body;
//
//                    followerList.setValue(successFollower);
//                }
                else if (body instanceof ChatRoomMake)
                {
                    ChatRoomMake result = (ChatRoomMake) response.body();
                    roomIdLive.setValue(result.getRoomId());
                }
            }
        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
        }
    }


    public class SingleLiveEvent<T> extends MutableLiveData<T> {

        private static final String TAG = "SingleLiveEvent";

        private final AtomicBoolean mPending = new AtomicBoolean(false);

        @MainThread
        public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

            if (hasActiveObservers()) {
                Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
            }

            // Observe the internal MutableLiveData
            super.observe(owner, new Observer<T>() {
                @Override
                public void onChanged(@Nullable T t) {
                    if (mPending.compareAndSet(true, false)) {
                        observer.onChanged(t);
                    }
                }
            });
        }

        @MainThread
        public void setValue(@Nullable T t) {
            mPending.set(true);
            super.setValue(t);
        }

        /**
         * Used for cases where T is Void, to make calls cleaner.
         */
        @MainThread
        public void call() {
            setValue(null);
        }
    }
}
