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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentHomeBinding;
import com.example.petmily.model.Place;
import com.example.petmily.model.Post_half;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMapSdk;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {
    private FragmentHomeBinding binding;
    Context context;
    FragmentManager fm;
    MapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        context = container.getContext();




        NaverMapSdk.getInstance(context).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("ztkfe47y3q"));

        fm = getChildFragmentManager();
        mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }



        RecyclerView re = binding.placeList;
        re.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<Place> test = new ArrayList<Place>();
        test.add(new Place("장소1"));
        test.add(new Place("장소2"));
        test.add(new Place("장소3"));
        test.add(new Place("장소4"));
        test.add(new Place("장소5"));
        Adapter_Place adapterPlace = new Adapter_Place(test);
        re.setAdapter(adapterPlace);



        RecyclerView post = binding.postList;
        post.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<Post_half> test2 = new ArrayList<Post_half>();
        test2.add(new Post_half("장소", R.drawable.ic_launcher_background));
        test2.add(new Post_half("장소2", R.drawable.ic_launcher_background));
        Adapter_Post_half adapterPosthafl = new Adapter_Post_half(test2);
        post.setAdapter(adapterPosthafl);






        SlidingUpPanelLayout sliding = binding.slidingLayout;
        sliding.setTouchEnabled(true);



        return view;
    }
}
