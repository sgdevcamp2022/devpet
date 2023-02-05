package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.dto.PasswordDto;
import com.example.shoh_oauth.data.dto.ProfileDto;
import com.example.shoh_oauth.data.entity.Profile;
import com.example.shoh_oauth.data.entity.User;
import com.example.shoh_oauth.exception.DataNotFoundException;
import com.example.shoh_oauth.repository.ProfileRepository;
import com.example.shoh_oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final VUserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;


    public void saveProfile(ProfileDto profileDto) {

        Profile profile = Profile.builder()
                .username(profileDto.getUsername())
                .name(profileDto.getName())
                .nickname(profileDto.getNickname())
                .build();

        profileRepository.save(profile);
    }

    public ProfileDto getProfile(String username) {

        Profile profile = profileRepository.findByUsername(username);

        ProfileDto profileDto = ProfileDto.builder()
                .username(profile.getUsername())
                .nickname(profile.getNickname())
                .my(profile.getMy())
                .imageUrl(profile.getImageUrl())
                .name(profile.getName())
                .build();

        return profileDto;
    }

    public void putProfile(String username, ProfileDto profileDto) {

        Profile profile = profileRepository.findByUsername(username);

        profile.setNickname(profileDto.getNickname());
        profile.setName(profileDto.getName());
        profile.setMy(profileDto.getMy());
        profile.setImageUrl(profileDto.getImageUrl());

        profileRepository.save(profile);
    }

    public void putPassword(String username, PasswordDto passwordDto) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("4003"));

        if (!passwordEncoder.matches(passwordDto.getNowPassword(), user.getPassword())) {
            throw new DataNotFoundException("4002");
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

}
