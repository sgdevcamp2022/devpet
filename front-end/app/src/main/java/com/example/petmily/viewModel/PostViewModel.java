package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.petmily.R;
import com.example.petmily.model.data.auth.local.AuthDatabase;
import com.example.petmily.model.data.post.Entity.ActionValue;
import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.Coord;
import com.example.petmily.model.data.post.Entity.HashTags;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;
import com.example.petmily.model.data.post.Entity.UserId;
import com.example.petmily.model.data.post.PostFull;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostHalf;
import com.example.petmily.model.data.post.local.PostDatabase;
import com.example.petmily.model.data.post.local.PostSQL;
import com.example.petmily.model.data.post.remote.API_Interface;
import com.example.petmily.model.data.post.remote.Action;
import com.example.petmily.model.data.post.remote.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class PostViewModel extends AndroidViewModel {
    final String URL = "http://121.187.22.37:5000/api/app/";

    final int POST_NUM = 10;

    FirebaseStorage storage;
    StorageReference storageReference;

    private PostCallback postCallback;
    private PostDatabase db;

    private API_Interface postInterface;
    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;

    private MutableLiveData<String> nickname;
    public MutableLiveData<String> getNickname() {
        if (nickname == null) {
            nickname = new MutableLiveData<String>();
        }
        return nickname;
    }

    private MutableLiveData<String> place;
    public MutableLiveData<String> getPlace() {
        if (place == null) {
            place = new MutableLiveData<String>();
        }
        return place;
    }

    private MutableLiveData<Boolean> likeCheck;
    public MutableLiveData<Boolean> getLikeCheck() {
        if (likeCheck == null) {
            likeCheck = new MutableLiveData<Boolean>();
        }
        return likeCheck;
    }

    private MutableLiveData<List<PostGrid>> postGrid;
    public MutableLiveData<List<PostGrid>> getPostGrid() {
        if (postGrid == null) {
            postGrid = new MutableLiveData<List<PostGrid>>();
        }
        return postGrid;
    }

    private MutableLiveData<List<PostHalf>> postHalf;
    public MutableLiveData<List<PostHalf>> getPostHalf() {
        if (postHalf == null) {
            postHalf = new MutableLiveData<List<PostHalf>>();
        }
        return postHalf;
    }

    private MutableLiveData<List<PostFull>> postFull;
    public MutableLiveData<List<PostFull>> getPostFull() {
        if (postFull == null) {
            postFull = new MutableLiveData<List<PostFull>>();
        }
        return postFull;
    }

    private MutableLiveData<CameraPosition> cameraPosion;
    public MutableLiveData<CameraPosition> getCameraPosition() {
        if (cameraPosion == null) {
            cameraPosion = new MutableLiveData<CameraPosition>();
        }
        return cameraPosion;
    }

    private MutableLiveData<List<Marker>> markerList;
    public MutableLiveData<List<Marker>> getMarkerList() {
        if (markerList == null) {
            markerList = new MutableLiveData<List<Marker>>();
        }
        return markerList;
    }

    private MutableLiveData<String> localName;
    public MutableLiveData<String> getLocalName() {
        if (localName == null) {
            localName = new MutableLiveData<String>();
        }
        return localName;
    }

    private MutableLiveData<List<String>> userIdLiveData;
    public MutableLiveData<List<String>> getUserIdLiveData() {
        if (userIdLiveData == null) {
            userIdLiveData = new MutableLiveData<List<String>>();
        }
        return userIdLiveData;
    }

    private SingleLiveEvent<Boolean> postEvent;
    public SingleLiveEvent<Boolean> getPostEvent()
    {
        if (postEvent == null) {
            postEvent = new SingleLiveEvent<Boolean>();
        }
        return postEvent;
    }
    private MutableLiveData<Integer> markerPosition;
    public MutableLiveData<Integer> getMarkerPosition() {
        if (markerPosition == null) {
            markerPosition = new MutableLiveData<Integer>();
        }
        return markerPosition;
    }

    private List<Comment> comments;
    private List<PostHalf> halfList;
    private List<PostGrid> gridList;
    private List<Marker> markers;
    private List<Uri> uriList;
    private List<Uri> viewpagerList;
    private List<PostSQL> postSQL;
    private List<Post> postList;
    private List<String> userIdList;
    private static List<Action> actionList;


    private double latitude;
    private double longitude;
    private String token;
    private String userId;





    public PostViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        init();
    }

    public void init()
    {
        db  = PostDatabase.getInstance(context);
        uriList = new ArrayList<Uri>();
        viewpagerList = new ArrayList<Uri>();
        userIdList = new ArrayList<String>();
        if(actionList != null) {
            actionList = new ArrayList<>();
        }



        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        userId = sharedPreferences.getString("userId", "");

        markers = new ArrayList<Marker>();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        postSQL = db.postDao().getPost();
        postHalf = new MutableLiveData<List<PostHalf>>();
        postFull = new MutableLiveData<List<PostFull>>();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        client.addInterceptor(new CustomInterceptor());
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient httpClient = client.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        postInterface = retrofit.create(API_Interface.class);
        postCallback = new PostCallback(context);

        if(postSQL != null)
        {
            List<Post> postList = new ArrayList<Post>();
            for(int i = 0; i < postSQL.size(); i++)
            {
                String createdAt = postSQL.get(i).getCreatedAt();
                String updatedAt = postSQL.get(i).getUpdatedAt();
                int feedId = postSQL.get(i).getFeedId();
                String content = postSQL.get(i).getContent();
                Location location = postSQL.get(i).getLocation();
                List<Integer> tagUsers = postSQL.get(i).getTagUsers();
                int groupId = 0;//null
                List<String> imageUrl = postSQL.get(i).getImageUrl();
                if(imageUrl.get(0).contains("post"))
                {
                    uriList.add(null);
                }
                else
                {
                    uriList.add(Uri.parse(imageUrl.get(0)));
                }


                int userId = postSQL.get(i).getUserId();
                userIdList.add(userId+"");
                HashTags hashTag = postSQL.get(i).getHashTag();
                String comments = postSQL.get(i).getComments();
                boolean favorite = postSQL.get(i).isFavorite();
                boolean used = postSQL.get(i).isUsed();
                postList.add(new Post(createdAt, updatedAt, feedId, content, location, tagUsers, groupId, imageUrl, userId, hashTag, comments, favorite, used));
            }
            this.postList = postList;
        }
        else
        {
            postSQL = new ArrayList<PostSQL>();
        }
    }
    public void postImport()
    {
        //테스트용 코드
//        List<String> imageUrl1 = new ArrayList<>();
//        imageUrl1.add("dog2.png");
        postList = new ArrayList<Post>();
        GpsTracker gpsTracker = new GpsTracker(context);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        longitude = longitude * -1;
        restApi = postInterface.getPost(latitude, longitude, 1000, "", postList.size(), postList.size()+POST_NUM+20, 1);
        restApi.enqueue(postCallback);


//        for(int i = 0; i < 10; i++)
//        {
//            Coord coord = new Coord(latitude+0.0001*i, longitude+0.0001*i);
//            Location location = new Location(2, "name", "address", 2, coord);
//            List<Integer> list = new ArrayList<Integer>();
//            Post post = new Post("createdAt", "updatedAt", 1, "content", location, list,
//                    0, imageUrl1, 1, new HashTags("해시태그"), "comments", true, true);
//
//            postList.add(post);
//        }
        List<PostSQL> postSQLList = new ArrayList<PostSQL>();
        for(int i = 0; i < postList.size(); i++)
        {
            String createdAt = postList.get(i).getCreatedAt();
            String updatedAt = postList.get(i).getUpdatedAt();
            int feedId = postList.get(i).getFeedId();
            String content = postList.get(i).getContent();
            Location location = postList.get(i).getLocation();
            List<Integer> tagUsers = postList.get(i).getTagUsers();
            int groupId = 0;//null
            List<String> imageUrl = postList.get(i).getImageUrl();
            int userId = postList.get(i).getUserId();
            HashTags hashTag = postList.get(i).getHashTag();
            String comments = postList.get(i).getComments();
            boolean favorite = postList.get(i).isFavorite();
            boolean used = postList.get(i).isUsed();
            postSQLList.add(new PostSQL(createdAt, updatedAt, feedId, content, location, tagUsers, groupId, imageUrl, userId, hashTag, comments, favorite, used));
        }
        postSQL = postSQLList;
        db.postDao().insertPost(postSQL);

    }
    public void setMarker()
    {
        markers = new ArrayList<Marker>();
        for(int i = 0; i < postList.size(); i++)
        {
            Coord coord = postList.get(i).getLocation().getCoord();
            Marker marker = new Marker();
            marker.setPosition(new LatLng(coord.getLatitude(), coord.getLonngitude()));
            marker.setZIndex(5000+i);
            marker.setIcon(MarkerIcons.GREEN);
            int finalI = i;
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    markerPosition.setValue(finalI);
                    return false;
                }
            });
            //marker.setHideCollidedMarkers(true);
            markers.add(marker);
        }
        markerList.setValue(markers);
    }

    public void postHalf(Profile profile)
    {
        halfList = new ArrayList<>();
        for(int i = 0; i < postList.size(); i++)
        {
            Coord coord = postList.get(i).getLocation().getCoord();
            String placename = "닉네임";
            //String placename = profile.getNickname();//일단 닉네임으로 진행
            String imageUrl = postList.get(i).getImageUrl().get(0);//첫 이미지만 가지고옴
            halfList.add(new PostHalf(coord, placename, uriList.get(i)));
        }
        getCurrentAddress(latitude, longitude);
        postHalf.setValue(halfList);
    }
    public void postGrid(Profile profile)
    {
        gridList = new ArrayList<>();
        postGrid.setValue(gridList);
        for(int i = 0; i < postList.size(); i++)
        {
            //String nickname = profile.getNickname();
            String nickname = "이름";
            String content = postList.get(i).getContent();
            String imageUrl = postList.get(i).getImageUrl().get(0);
            String time = postList.get(i).getCreatedAt();
            SimpleDateFormat oldTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date currentDay = oldTime.parse(time, new ParsePosition(0));
            Long currentLong = currentDay.getTime();
            time = formatTimeString(currentLong);

            gridList.add(new PostGrid(uriList.get(i), nickname, content, time));
        }
        postGrid.setValue(gridList);
    }
    public void postFull()
    {
        List<PostFull> list = new ArrayList<>();
        for(int i = 0; i < postList.size(); i++)
        {
            String createdAt = postList.get(i).getCreatedAt();
            SimpleDateFormat oldTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date currentDay = oldTime.parse(createdAt, new ParsePosition(0));
            Long currentLong = currentDay.getTime();
            createdAt = formatTimeString(currentLong);
            String updatedAt = postList.get(i).getUpdatedAt();
            int feedId = postList.get(i).getFeedId();
            String content = postList.get(i).getContent();
            Location location = postList.get(i).getLocation();
            List<Integer> tagUsers = postList.get(i).getTagUsers();
            int groupId = 0;//null
            List<String> imageUrl = postList.get(i).getImageUrl();
            int userId = postList.get(i).getUserId();
            HashTags hashTag = postList.get(i).getHashTag();
            String comments = postList.get(i).getComments();
            boolean favorite = postList.get(i).isFavorite();
            boolean used = postList.get(i).isUsed();
            list.add(new PostFull(createdAt, updatedAt, feedId, content, location, tagUsers, groupId, uriList, userId, hashTag, comments, favorite, used));
            postFull.setValue(list);
//            for(int j = 0; j < imageUrl.size(); j++)
//            {
//                storageReference.child(imageUrl.get(j)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        list.add(new PostFull(createdAt, updatedAt, feedId, content, location, tagUsers, groupId, uriList, userId, hashTag, comments, favorite, used));
//                        postFull.setValue(list);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        
//                    }
//                });
//            }
        }
    }

    public void actionAdd(long time, int position)//확대 추가
    {
        long newTime = System.currentTimeMillis();
        newTime -= time;

        String postId = postList.get(position).getFeedId()+"";

        double score = 0;
        score += (newTime/1000)*0.1;
        if(postList.get(position).isFavorite())
            score += 1;
        if(postList.get(position).getComments() != null)
            score += 1;


        actionList.add(new Action(userId, postId, score));


    }
    public void postAction()
    {
        if(actionList.size() >= 1) {
            restApi = postInterface.actionResult(actionList);
            restApi.enqueue(postCallback);
        }
    }
    public void postMy()
    {
        restApi = postInterface.getPost(latitude, longitude, 50000, "", postList.size(), postList.size()+POST_NUM+20, 1);
        restApi.enqueue(postCallback);
    }
    public void postSearch()
    {
//        postList = new ArrayList<Post>();
//        Call<List<String>> restApi = postInterface.getSearch(new UserId(userId));
//        restApi.enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.code() == 200)
//                {
//                    List<String> body = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//
//            }
//        });
    }
    public void moveMap(int position)
    {
        if(position != -1) {
            Coord coord = halfList.get(position).getCoord();
            CameraPosition cameraPosition = new CameraPosition(
                    new LatLng(coord.getLatitude(), coord.getLonngitude()),  // 위치 지정
                    18                         // 줌 레벨
            );
            this.cameraPosion.setValue(cameraPosition);
            markerList.setValue(markers);
        }
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
        if(address.getThoroughfare() != null)//동
        {
            localName.setValue(address.getThoroughfare());
            Log.e("getThoroughfare", address.getThoroughfare());
        }
        else if(address.getFeatureName() != null)//지번
        {
            localName.setValue(address.getFeatureName());
        }
        else if(address.getLocality() != null)//군, 구
        {
            localName.setValue(address.getFeatureName());
        }
        return address.getAddressLine(0).toString()+"\n";
    }
    public class PostCallback<T> implements retrofit2.Callback<T> {

        final int SUCCESS               = 200;

        final int INVALID_PARAMETER     = 400;
        final int NEED_LOGIN            = 401;
        final int UNAUTHORIZED          = 403;
        final int NOT_FOUND             = 404;

        final int INTERNAL_SERVER_ERROR = 500;


        Context context;

        public PostCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(retrofit2.Call<T> call, retrofit2.Response<T> response) {
            int responseCode = response.code();//네트워크 탐지할 때 사용 코드
            T body = response.body();

            if(responseCode == SUCCESS)
            {
                List<Post> posts = (List<Post>) body;
                Log.e("포스트 통신ㅁㄴㅇㄴㅁㅇㄴㅁㅇ : ", posts.size()+"");
                for(int i = 0; i < posts.size(); i++)
                {
                    postList.add(posts.get(i));
                }
                List<PostSQL> postSQLList = new ArrayList<PostSQL>();
                for(int i = 0; i < postList.size(); i++)
                {
                    String createdAt = postList.get(i).getCreatedAt();
                    String updatedAt = postList.get(i).getUpdatedAt();
                    int feedId = postList.get(i).getFeedId();
                    String content = postList.get(i).getContent();
                    Location location = postList.get(i).getLocation();
                    List<Integer> tagUsers = postList.get(i).getTagUsers();
                    int groupId = 0;//null
                    List<String> imageUrl = postList.get(i).getImageUrl();
                    if(imageUrl.get(0).contains("post"))
                    {
                        uriList.add(null);
                    }
                    else
                    {
                        uriList.add(Uri.parse(imageUrl.get(0)));
                    }

                    int userId = postList.get(i).getUserId();
                    userIdList.add(userId+"");
                    HashTags hashTag = postList.get(i).getHashTag();
                    String comments = postList.get(i).getComments();
                    boolean favorite = postList.get(i).isFavorite();
                    boolean used = postList.get(i).isUsed();
                    postSQLList.add(new PostSQL(createdAt, updatedAt, feedId, content, location, tagUsers, groupId, imageUrl, userId, hashTag, comments, favorite, used));
                }
                postSQL = postSQLList;
                db.postDao().insertPost(postSQL);
                //비동기 처리를 위한 큐 생성
                Queue<Integer> queue = new LinkedList<>();
                for(int i = 0; i < postList.size(); i++)
                {
                    for(int j = 0; j < postList.get(i).getImageUrl().size(); j++)
                    {
                        String uri = postList.get(i).getImageUrl().get(j);
                            uriList.add(Uri.parse(uri));
                            Log.e("Uri : ", uri);

//                        storageReference.child(uri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                uriList.add(uri);
//                                queue.add(0);
//                                if(queue.size() == 10)
//                                {
//                                    userIdLiveData.setValue(userIdList);
//                                    //postEvent.setValue(true);
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.e("스토리지 이미지 불러오기 실패 : ", e.getMessage());
//                                queue.add(0);
//                                if(queue.size() == 10)
//                                {
//                                    userIdLiveData.setValue(userIdList);
//                                    //postEvent.setValue(true);
//                                }
//                            }
//                        });
                    }

                }
                postHalf(null);
                postGrid(null);
                postFull();
            }
            else
            {
                Log.e("포스트 통신 에러 : ", responseCode+"");
                try {
                    Log.e("포스트 통신 에러 : ", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
            Log.e("포스트 통신 실패 에러 : ", t.toString());
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

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }
}