package com.example.petmily.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.petmily.R;
import com.example.petmily.databinding.FragmentProfileBinding;

public class Fragment_Profile extends Fragment {

    private FragmentProfileBinding binding;
    private Fragment_Message fragment_message;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();
        context = container.getContext();


        Fragment_Post fragment_post = new Fragment_Post();


        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.profile_frame, fragment_post).commitAllowingStateLoss();


                /*
            @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam List<String> userId) {
        ChatRoom chatRoom = ChatRoom.create(userId);
        return chatRoomRepository.createChatRoom(chatRoom);
    }
         */

        return view;
    }


}
