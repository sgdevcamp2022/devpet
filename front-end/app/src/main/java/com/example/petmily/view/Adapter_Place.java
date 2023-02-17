package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.model.Place;
import com.example.petmily.R;
import com.example.petmily.databinding.PlaceListBinding;

import java.util.ArrayList;

public class Adapter_Place extends RecyclerView.Adapter<Adapter_Place.Holder>{
    ArrayList<Place> list;

    public Adapter_Place(ArrayList<Place> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlaceListBinding placeListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.place_list,
                parent,
                false
        );
        return new Holder(placeListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Place place = list.get(position);
        holder.placeListBinding.setPlace(place);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private PlaceListBinding placeListBinding;

        public Holder(@NonNull PlaceListBinding placeListBinding) {
            super(placeListBinding.getRoot());
            this.placeListBinding=placeListBinding;
        }
    }
}




