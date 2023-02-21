package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentAlarmBinding;
import com.example.petmily.model.Alarm;
import com.example.petmily.model.Place;

import java.util.ArrayList;

public class Fragment_Alarm extends Fragment {

    private FragmentAlarmBinding binding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_alarm, container, false);
        View view = binding.getRoot();
        context = container.getContext();


        RecyclerView day = binding.dayList;
        day.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ArrayList<Alarm> test = new ArrayList<Alarm>();
        test.add(new Alarm(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용"));
        test.add(new Alarm(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용"));
        test.add(new Alarm(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용"));
        test.add(new Alarm(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용"));
        test.add(new Alarm(R.drawable.ic_launcher_background, "이름" , "대충 알림 내용"));

        Adapter_Alarm adapter_alarm = new Adapter_Alarm(test);
        day.setAdapter(adapter_alarm);

        RecyclerView week = binding.weekList;
        week.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        week.setAdapter(adapter_alarm);

        RecyclerView month = binding.monthList;
        month.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        month.setAdapter(adapter_alarm);



        return view;
    }

}
