package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.Profile;
import com.smilegate.devpet.appserver.model.ProfileRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public long postProfile(@RequestBody ProfileRequest profileRequest,UserInfo userInfo)
    {
        Profile result = profileService.postProfile(profileRequest, userInfo);
        return result.getProfileId();
    }

    @PutMapping
    public Long putProfile(@RequestBody ProfileRequest profileRequest)
    {
        Profile result = profileService.putProfile(profileRequest);
        return result.getProfileId();
    }
    @GetMapping("/{profileId}")
    public Profile getUserProfile(@PathVariable("profileId") Long profileId)
    {
        Profile findProfile = profileService.getProfile(profileId);
        return findProfile;
    }
    @GetMapping("/my-profile")
    public Profile getMyProfile(UserInfo userInfo)
    {
        Profile findProfile = profileService.getProfile(userInfo);
        return findProfile;
    }
    @GetMapping("/{profileId}/folower")
    public List<Profile> getFollowerList(@PathVariable("profileId") Long profileId)
    {
        List<Profile> followers = profileService.getFollowerList(profileId);
        return followers;
    }
    @GetMapping("/{profileId}/folow")
    public List<Profile> getFollowList(@PathVariable("profileId") Long profileId)
    {
        List<Profile> follows = profileService.getFollowList(profileId);
        return follows;
    }

}