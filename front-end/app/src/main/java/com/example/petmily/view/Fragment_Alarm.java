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

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentAlarmBinding;


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
        return view;
    }

}
