package com.example.petmily.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.AlarmListBinding;
import com.example.petmily.databinding.PlaceListBinding;
import com.example.petmily.model.Alarm;
import com.example.petmily.model.Place;

import java.util.ArrayList;

public class Adapter_Alarm extends RecyclerView.Adapter<Adapter_Alarm.Holder>{
    ArrayList<Alarm> list;

    public Adapter_Alarm(ArrayList<Alarm> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_Alarm.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlarmListBinding alarmListBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.alarm_list,
                parent,
                false
        );
        return new Adapter_Alarm.Holder(alarmListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Alarm.Holder holder, int position) {
        Alarm alarm = list.get(position);
        holder.alarmListBinding.setAlarm(alarm);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private AlarmListBinding alarmListBinding;

        public Holder(@NonNull AlarmListBinding alarmListBinding) {
            super(alarmListBinding.getRoot());
            this.alarmListBinding=alarmListBinding;
        }
    }
}
