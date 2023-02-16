package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.petmily.R;
import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.Coord;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;
import com.example.petmily.model.data.post.PostGrid;
import com.example.petmily.model.data.post.PostHalf;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostViewModel extends AndroidViewModel {

    final String URL = "https://121.187.37.22:5555/api/app/";

    FirebaseStorage storage;
    StorageReference storageReference;

    //private AuthDatabase db;
    private PostCallback postCallback;
    private List<Post> postList;

    private API_Interface postInterface;
    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;

    //private TokenSQL token;


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

    private MutableLiveData<List<Post>> postFull;
    public MutableLiveData<List<Post>> getPostFull() {
        if (postFull == null) {
            postFull = new MutableLiveData<List<Post>>();
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






    int like;

    private List<Comment> comments;
    private List<PostHalf> halfList;
    private List<PostGrid> gridList;
    private List<Marker> markers;
    



    public PostViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        init();
    }

    public void init()
    {

        markers = new ArrayList<Marker>();

        //테스트용 코드
        postList = new ArrayList<Post>();
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("dog2.png");
        GpsTracker gpsTracker = new GpsTracker(context);


        for(int i = 0; i < 10; i++)
        {
            //38.2078015 	127.2129742
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Coord coord = new Coord(latitude+0.0001*i, longitude+0.0001*i);
            Location location = new Location(2, coord);
            Post post = new Post(null, location, imageUrl, 3, true, "대충 게시글 내용",
                    null, null);
            postList.add(post);
        }





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


    }
    //rest통신으로 포스트 불러오기
    public void postImport()
    {

        restApi = postInterface.getPost();
        restApi.enqueue(postCallback);



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
            storageReference.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //halfList.add(new PostHalf(coord,placename, R.drawable.ic_launcher_background));//테스트용 이미지
                    halfList.add(new PostHalf(coord,placename, uri));
                    postHalf.setValue(halfList);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Log.e("파일 불러오기 실패 : ", e.getMessage());
                }
            });
            //테스트 코드


            /*
            Marker marker = new Marker();
            marker.setPosition(new LatLng(37.8656208+x, 127.750391+y));
            marker.setHeight(100);
            marker.setWidth(100);
            marker.setIcon(OverlayImage.fromResource(R.drawable.dm));
            markers.add(marker);
            markerList.setValue(markers);

            Coord temp = new Coord(37.8656208+x, 127.750391+y);
            coord.setLatitude(37.8656208+i);
            coord.setLonngitude(127.750391+i);
            PostHalf p = new PostHalf(temp,placename, R.drawable.ic_launcher_background);
            halfList.add(p);//테스트용 이미지
            //list.add(new PostHalf(coord,placename, uri.toString()));
            postHalf.setValue(halfList);


             */

        }
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
            storageReference.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    gridList.add(new PostGrid(uri, nickname, content, time));
                    postGrid.setValue(gridList);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Log.e("파일 불러오기 실패 : ", e.getMessage());
                }
            });


            //Uri uri = null;
            //gridList.add(new PostGrid(uri, nickname, content, time));
            //postGrid.setValue(gridList);


        }
    }

    public void postFull()
    {
        List<Post> list = new ArrayList<>();
        for(int i = 0; i < postList.size(); i++)
        {
            Profile profile = postList.get(i).getProfile();
            Location location = postList.get(i).getLocation();
            List<String> imageUrl = postList.get(i).getImageUrl();
            int like = postList.get(i).getLike();
            boolean likeCheck = postList.get(i).isLikeCheck();
            String content = postList.get(i).getContent();
            List<String> hashTag = postList.get(i).getHashTag();
            List<Comment> comments = postList.get(i).getComments();

            for(int j = 0; j < imageUrl.size(); j++)
            {
                storageReference.child(imageUrl.get(j)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        list.add(new Post(profile, location, imageUrl, like, likeCheck, content ,hashTag, comments));
                        postFull.setValue(list);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.e("파일 불러오기 실패 : ", e.getMessage());
                    }
                });
            }
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
                    postList = (List<Post>) body;
                    postHalf();
                    postGrid();
                }
            }
        }
        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
        }
    }
}