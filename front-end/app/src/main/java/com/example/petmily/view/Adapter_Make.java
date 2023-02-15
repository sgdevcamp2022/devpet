package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.MakeListBinding;
import com.example.petmily.model.data.profile.make.Make;

import java.util.List;

public class Adapter_Make extends RecyclerView.Adapter<Adapter_Make.Holder>{
    List<Make> list;

    public Adapter_Make(List<Make> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Make.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MakeListBinding makeListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.make_list,
                parent,
                false
        );
        return new Adapter_Make.Holder(makeListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Make.Holder holder, int position) {
        Make make = list.get(position);
        holder.makeListBinding.setMake(make);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private MakeListBinding makeListBinding;

        public Holder(@NonNull MakeListBinding makeListBinding) {
            super(makeListBinding.getRoot());
            this.makeListBinding=makeListBinding;
        }
    }
}
