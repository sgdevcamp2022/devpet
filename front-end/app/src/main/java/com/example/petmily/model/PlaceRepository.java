package com.example.petmily.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {
    private MutableLiveData<List<Place>> allPlace = new MutableLiveData<>();

    public PlaceRepository() {
        List<Place> Places = new ArrayList<>();
        Places.add(new Place("장소"));
        allPlace.setValue(Places);
    }

    public LiveData<List<Place>> findAll() {
        return allPlace;
    }



    public void delete(Place place) {
        List<Place> places = allPlace.getValue();
        places.remove(place);
        allPlace.setValue(places);
    }



    public void save(Place place) {
        List<Place> Places = allPlace.getValue();
        Places.add(place);
        allPlace.setValue(Places);
    }
}
