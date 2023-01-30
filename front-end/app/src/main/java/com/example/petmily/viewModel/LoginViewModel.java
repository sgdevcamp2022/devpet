package com.example.petmily.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.petmily.model.Place;
import com.example.petmily.model.PlaceRepository;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginViewModel  extends AndroidViewModel {

    //ViewModel이 가진 데이터
    private LiveData<List<Place>> allPlaces;
    private PlaceRepository placeRepository=new PlaceRepository();

    public LoginViewModel(@NonNull Application application) {
        super(application);



    }


    public void updateKakaoLoginUi()
    {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null)
                {
                    Log.e("테스트\t" , "" + user.getId());
                    Log.e("테스트\t" , "" + user.getKakaoAccount().getEmail());




                }

                return null;
            }
        });

    }
    public LiveData<List<Place>> findAll(){
        return allPlaces;
    }

    public void save(Place note){
        placeRepository.save(note);
    }
    public void remove(Place note){
        placeRepository.delete(note);
    }
    /*public LiveData<List<Note>> 전체보기(){
        return noteRepository.findAll();
    }*/
}
