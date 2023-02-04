package com.example.petmily.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.model.Place;
import com.example.petmily.model.PlaceRepository;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel{

    //ViewModel이 가진 데이터
    private LiveData<List<Place>> allPlaces;
    private PlaceRepository placeRepository=new PlaceRepository();

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        allPlaces=placeRepository.findAll();



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

