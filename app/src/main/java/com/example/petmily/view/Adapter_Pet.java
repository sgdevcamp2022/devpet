package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.PetListBinding;
import com.example.petmily.model.data.profile.Pet;

import java.util.List;

public class Adapter_Pet extends RecyclerView.Adapter<Adapter_Pet.Holder> {
    List<Pet> list;

    public Adapter_Pet(List<Pet> list) {
        this.list = list;
    }

    public void setItemList(List<Pet> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_Pet.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PetListBinding petListBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.pet_list,
                parent,
                false
        );
        return new Adapter_Pet.Holder(petListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Pet.Holder holder, int position) {
        Pet chat = list.get(position);

        ImageView imageView = holder.petListBinding.petImage;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.petListBinding.setPet(chat);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private PetListBinding petListBinding;

        public Holder(@NonNull PetListBinding petListBinding) {
            super(petListBinding.getRoot());
            this.petListBinding = petListBinding;


        }
    }
}