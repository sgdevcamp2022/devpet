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
import com.example.petmily.model.data.profile.remote.Success;
import com.example.petmily.model.data.profile.remote.SuccessFollow;
import com.example.petmily.model.data.profile.remote.SuccessFollower;
import com.example.petmily.model.data.profile.remote.SuccessTest;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private String userId;
    private Uri imageUri;

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
        userId = sharedPreferences.getString("userId", "");
        //db = ProfileDatabase.getInstance(context);
        profileCallback = new ProfileCallback(context);




//
//        val client = OkHttpClient.Builder()
//                .readTimeout(10, TimeUnit.SECONDS)
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .build()
//        retrofit = Retrofit.Builder().baseUrl(API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build()
//









        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        client.addInterceptor(new CustomInterceptor());

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

        Pet pet = new Pet(name, division, birth, about, imageUri, "2023-02-19");
        pets.add(pet);
        petList.setValue(pets);

    }
    public void profileSave(String imageUri, String nickname, String about, String birth)
    {
        //this.imageUri = imageUri;
        this.imageUri = null;
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

            ResponseBody errorBody = response.errorBody();

            Log.e("프로필 통신 확인 : ", responseCode+"");

            if(errorBody != null)
            {
                try {
                    Log.e("프로필 통신 확인 : ", errorBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Profile result = (Profile) body;
                Log.e("프로필 통신 확인 : ",  result.toString());
            }




            if(responseCode == SUCCESS) {
                if (body instanceof Profile) {
                    firebaseStorage = FirebaseStorage.getInstance();
                    storageRef = firebaseStorage.getReference();
                    Profile result = (Profile) body;
                    profile.setValue(result);
                    //팔로잉 여부 반환
//                    followEvent.setValue(result.getFollow());

                    //프로필 이미지 불러오기
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
               else if (body instanceof Success)//프로필 등록 성공시
                {
//                    Date date = new Date(System.currentTimeMillis());
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                    String getTime = dateFormat.format(date);
//                    UploadTask uploadTask = storageReference.child("profile/"+userId+"/"+getTime+".jpg").putFile(imageUri);
//
//                    //UploadTask uploadTask = storageReference.child("dog2.png").putFile(imageUri.get(i));
//                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e("스토리지 저장 실패 : ", e.toString());
//                            e.printStackTrace();
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Log.e("스토리지 저장 성공 : ", taskSnapshot.getMetadata().getPath());
//                        }
//                    });
//                    for(int i = 0; i < pets.size(); i++)//펫 프로필사진 저장
//                    {
//                        uploadTask = storageReference.child("profile/"+userId+"/pets/"+getTime+".jpg").putFile(imageUri);//pets.get(i).getImageUrl();
//
//                        //UploadTask uploadTask = storageReference.child("dog2.png").putFile(imageUri.get(i));
//                        uploadTask.addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.e("스토리지 저장 실패 : ", e.toString());
//                                e.printStackTrace();
//                            }
//                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Log.e("스토리지 저장 성공 : ", taskSnapshot.getMetadata().getPath());
//                            }
//                        });
//                    }
                }
            }
            else if(responseCode == INTERNAL_SERVER_ERROR)
            {
                Profile result = (Profile) body;
                profile.setValue(result);
                //profile.setValue(null);
            }
            else
            {


            }
        }


        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
            Log.e("프로필 통신 실패 : ", t.getMessage());
            t.printStackTrace();
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
