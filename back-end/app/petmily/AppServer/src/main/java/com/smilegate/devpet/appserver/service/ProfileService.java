package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Profile;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    public Profile getProfile(Principal principal)
    {
        return profileRepository.findByUserInfo((UserInfo)principal).orElseThrow(RuntimeException::new);
    }

}
