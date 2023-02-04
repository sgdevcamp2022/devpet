package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Profile;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    public Profile getProfile(UserInfo userInfo)
    {
        return profileRepository.findByUserInfo(userInfo).orElseThrow(RuntimeException::new);
    }

}
