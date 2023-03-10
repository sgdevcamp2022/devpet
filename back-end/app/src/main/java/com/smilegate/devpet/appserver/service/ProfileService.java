package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.api.relation.PetApi;
import com.smilegate.devpet.appserver.api.relation.UserInfoApi;
import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.repository.mongo.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final SequenceGeneratorService profileSequenceGeneratorService;
    private final PetService petService;
    private final PetApi petRelationService;
    private final UserInfoApi userInfoService;
    public Profile postProfile(ProfileRequest profileRequest,UserInfo userInfo)
    {

        AtomicReference<Profile> profile = new AtomicReference<>();
        profileRepository.findByUsername(userInfo.getUsername()).ifPresent((item)->{
            item.setProfileData(profileRequest);
            profile.set(item);
        });
        if(profile.get()==null)
        {
            Profile newProfile = new Profile(profileRequest, userInfo);
            newProfile.setProfileId(profileSequenceGeneratorService.longSequenceGenerate(Profile.SEQUENCE_NAME));
            profile.set(newProfile);
        }
        Profile saveProfile = profile.get();
        profile.get().getPetList().forEach(item->{item.setProfileId(saveProfile.getProfileId());});
        List<Pet> savePetList = petService.postAllPet(profile.get().getPetList());
        Profile result = profileRepository.save(saveProfile);
        sendToRelationServer(saveProfile,userInfo);
        return result;
    }

    public Profile putProfile(ProfileRequest profileRequest, UserInfo userInfo)
    {
        Profile profile = profileRepository.findById(profileRequest.getId()).orElseThrow(NullPointerException::new);
        profile.setProfileData(profileRequest);
        List<Pet> savePetList = petService.postAllPet(profile.getPetList());
        Profile result = profileRepository.save(profile);
        sendToRelationServer(profile, userInfo);
        return result;
    }
    public void sendToRelationServer(Profile profile, UserInfo userInfo)
    {
        List<Pet> petList = profile.getPetList();
        String username = userInfo.getUsername();
        List<PetInfoDto> petInfoDtos = petList.stream().map(item->
                PetInfoDto.builder()
                        .petId(item.getPetId().toString())
                        .petBirth(item.getBirth().toString())
                        .petName(item.getName())
                        .tags(item.getTags())
                        .userId(username).build()
        ).collect(Collectors.toList());
        userInfoService.saveUserInfo(UserInfoDto.builder()
                .nickname(profile.getNickname())
                .userId(username)
                .birth(profile.getBirth().toString())
                .build()
        );
        petRelationService.savePet(petInfoDtos);
    }

    public Profile getProfile(Long profileId)
    {
        Profile result = profileRepository.findById(profileId).orElseThrow(NullPointerException::new);
        setProfileFollowAnFollowerCount(result);
        return result;
    }
    public List<Pet> getUserPet(Long profileId)
    {
        List<Pet> result = petService.getUserPetList(profileId);
        if (result==null)
            return new ArrayList<>();
        return result;
    }
    public Profile getProfile(UserInfo userInfo)
    {
        Profile result = getProfileByUsername(userInfo.getUsername());
        return result;
    }
    public Profile getProfileByUsername(String username)
    {
        Profile result = profileRepository.findByUsername(username).orElseThrow(NullPointerException::new);
        setProfileFollowAnFollowerCount(result);
        return result;
    }
    private void setProfileFollowAnFollowerCount(Profile profile)
    {
        profile.setFollower(userInfoService.countFollower(profile.getUsername()));
        profile.setFollow(userInfoService.countFollowing(profile.getUsername()));
    }
    public List<Profile> getFollowerList(Long profileId)
    {
        Profile profile = profileRepository.findById(profileId).orElseThrow(NullPointerException::new);

        List<String> followerUserIds = new ArrayList<>(userInfoService.getFollowerList(
                profile.getUsername()
        ));
        List<Profile> result = profileRepository.findByUsernameIn(followerUserIds);
        return result;
    }

    public List<Profile> getFollowList(Long profileId)
    {
        Profile profile = profileRepository.findById(profileId).orElseThrow(NullPointerException::new);

        List<String> followUsernames = new ArrayList<>(userInfoService.getFollowingList(
                profile.getUsername()
        ));
        List<Profile> result = profileRepository.findByUsernameIn(followUsernames);
        return result;
    }
}
