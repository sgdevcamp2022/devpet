package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
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
import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.Coord;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;
import com.example.petmily.model.data.post.PostFull;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostHalf;
import com.example.petmily.model.data.post.local.PostDatabase;
import com.example.petmily.model.data.post.local.PostSQL;
import com.example.petmily.model.data.post.remote.API_Interface;
import com.example.petmily.model.data.post.remote.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostViewModel extends AndroidViewModel {

    final String URL = "https://121.187.37.22:5555/api/app/";

    FirebaseStorage storage;
    StorageReference storageReference;

    private PostCallback postCallback;
    private List<Post> postList;
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

    private SingleLiveEvent<Boolean> postEvent;
    public SingleLiveEvent<Boolean> getPostEvent()
    {
        if (postEvent == null) {
            postEvent = new SingleLiveEvent<Boolean>();
        }
        return postEvent;
    }


    int like;

    private List<Comment> comments;
    private List<PostHalf> halfList;
    private List<PostGrid> gridList;
    private List<Marker> markers;
    private List<Uri> uriList;
    private List<Uri> viewpagerList;
    private List<PostSQL> postSQL;

    private double latitude;
    private double longitude;



    public PostViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        init();
    }

    public void init()
    {
        db  = PostDatabase.getInstance(context);
        uriList = new ArrayList<>();
        viewpagerList = new ArrayList<>();

        markers = new ArrayList<Marker>();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        postInterface = retrofit.create(API_Interface.class);
        postCallback = new PostCallback(context);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //postImport();
        //postHalf();
        //postGrid();



        postSQL = db.postDao().getPost();

        if(postSQL != null)
        {
            List<Post> postList = new ArrayList<Post>();
            for(int i = 0; i < postSQL.size(); i++)
            {
                String postId = postSQL.get(i).getPostId();
                Profile profile = postSQL.get(i).getProfile();
                Location location = postSQL.get(i).getLocation();
                List<String> imageUrl = postSQL.get(i).getImageUrl();
                int like = postSQL.get(i).getLike();
                boolean likeCheck = postSQL.get(i).isLikeCheck();
                String content = postSQL.get(i).getContent();
                List<String> hashTag = postSQL.get(i).getHashTag();
                List<Comment> comments = postSQL.get(i).getComments();

                postList.add(new Post(postId, profile, location, imageUrl, like, likeCheck, content ,hashTag, comments));
            }
            this.postList = postList;
        }
        else
        {
            postSQL = new ArrayList<PostSQL>();
        }






    }
    //rest통신으로 포스트 불러오기 구현 x
    public void postImport()
    {
//        restApi = postInterface.getPost();
//        restApi.enqueue(postCallback);

        //테스트용 코드
        postList = new ArrayList<Post>();
        List<String> imageUrl1 = new ArrayList<>();
        imageUrl1.add("dog2.png");
        GpsTracker gpsTracker = new GpsTracker(context);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        for(int i = 0; i < 10; i++)
        {
            //38.2078015 	127.2129742

            Coord coord = new Coord(latitude+0.0001*i, longitude+0.0001*i);
            Location location = new Location(2, coord);
            Profile profile = new Profile(imageUrl1.get(0), i+"", "1번유저 닉네임");
            Post post = new Post(i+"" ,profile, location, imageUrl1, 3, true, "대충 게시글 내용",
                    null, null);
            postList.add(post);
        }
        final int[] count = {0};
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < postList.get(i).getImageUrl().size(); j++)
            {
                String uri = postList.get(i).getImageUrl().get(j);
                storageReference.child(uri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uriList.add(uri);
                        count[0]++;
                        if(count[0] == 10)
                        {
                            postEvent.setValue(true);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.e("파일 불러오기 실패 : ", e.getMessage());
                        count[0]++;
                        if(count[0] == 10)
                        {
                            postEvent.setValue(true);
                        }
                    }
                });
            }

        }



        List<PostSQL> postSQLList = new ArrayList<PostSQL>();
        for(int i = 0; i < postList.size(); i++)
        {
            String postId = postList.get(i).getPostId();
            Profile profile = postList.get(i).getProfile();
            Location location = postList.get(i).getLocation();
            List<String> imageUrl = postList.get(i).getImageUrl();
            int like = postList.get(i).getLike();
            boolean likeCheck = postList.get(i).isLikeCheck();
            String content = postList.get(i).getContent();
            List<String> hashTag = postList.get(i).getHashTag();
            List<Comment> comments = postList.get(i).getComments();

            postSQLList.add(new PostSQL(postId, profile, location, imageUrl, like, likeCheck, content ,hashTag, comments));
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
            //marker.setHideCollidedMarkers(true);
            markers.add(marker);
        }
        markerList.setValue(markers);
    }

    public void postHalf()
    {
        halfList = new ArrayList<>();
        for(int i = 0; i < postList.size(); i++)
        {
            Coord coord = postList.get(i).getLocation().getCoord();
            String placename = "닉네임";
            //String placename = postList.get(i).getProfile().getNickname();//일단 닉네임으로 진행
            String imageUrl = postList.get(i).getImageUrl().get(0);//첫 이미지만 가지고옴
            halfList.add(new PostHalf(coord, placename, uriList.get(i)));
        }
        getCurrentAddress(latitude, longitude);
        postHalf.setValue(halfList);
    }
    public void postGrid()
    {
        gridList = new ArrayList<>();
        postGrid.setValue(gridList);
        for(int i = 0; i < postList.size(); i++)
        {
            //String nickname = postList.get(i).getProfile().getNickname();
            String nickname = "닉네임";
            String content = postList.get(i).getContent();
            String imageUrl = postList.get(i).getImageUrl().get(0);
            String time = "1시간 전";
            //String time = getTime();
            gridList.add(new PostGrid(uriList.get(i), nickname, content, time));
        }
        postGrid.setValue(gridList);
    }
    public void postFull()
    {
        List<PostFull> list = new ArrayList<>();
        for(int i = 0; i < postList.size(); i++)
        {
            String postId = postList.get(i).getPostId();
            Profile profile = postList.get(i).getProfile();
            Location location = postList.get(i).getLocation();
            //List<String> imageUrl = uriList.get(i).getPath();
            int like = postList.get(i).getLike();
            boolean likeCheck = postList.get(i).isLikeCheck();
            String content = postList.get(i).getContent();
            List<String> hashTag = postList.get(i).getHashTag();
            List<Comment> comments = postList.get(i).getComments();

            list.add(new PostFull(postId, profile, location, uriList, like, likeCheck, content ,hashTag, comments));
            postFull.setValue(list);

//            for(int j = 0; j < imageUrl.size(); j++)
//            {
//                storageReference.child(imageUrl.get(j)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        list.add(new Post(postId, profile, location, imageUrl, like, likeCheck, content ,hashTag, comments));
//                        postFull.setValue(list);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Log.e("파일 불러오기 실패 : ", e.getMessage());
//                    }
//                });
//            }
        }
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
            markers.get(position).setWidth(150);
            markers.get(position).setHeight(150);
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
        //Log.e("주소 테스트 : ", address.getLocality());
        localName.setValue(address.getLocality());

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

            Gson gson = new Gson();
            int responseCode = response.code();//네트워크 탐지할 때 사용 코드
            T body = response.body();

            if(responseCode == SUCCESS)
            {
                if(body instanceof Post)
                {
                    List<Post> posts = (List<Post>) body;
                    for(int i = 0; i < posts.size(); i++)
                    {
                        postList.add(posts.get(i));

                    }

                    List<PostSQL> postSQLList = new ArrayList<PostSQL>();
                    for(int i = 0; i < postList.size(); i++)
                    {
                        String postId = postList.get(i).getPostId();
                        Profile profile = postList.get(i).getProfile();
                        Location location = postList.get(i).getLocation();
                        List<String> imageUrl = postList.get(i).getImageUrl();
                        int like = postList.get(i).getLike();
                        boolean likeCheck = postList.get(i).isLikeCheck();
                        String content = postList.get(i).getContent();
                        List<String> hashTag = postList.get(i).getHashTag();
                        List<Comment> comments = postList.get(i).getComments();

                        postSQLList.add(new PostSQL(postId, profile, location, imageUrl, like, likeCheck, content ,hashTag, comments));
                    }
                    postSQL = postSQLList;
                    db.postDao().insertPost(postSQL);

                    postHalf();
                    postGrid();
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