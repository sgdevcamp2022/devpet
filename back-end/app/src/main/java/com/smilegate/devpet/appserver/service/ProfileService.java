package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.api.relation.PetApi;
import com.smilegate.devpet.appserver.api.relation.UserInfoApi;
import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.repository.mongo.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final SequenceGeneratorService profileSequenceGeneratorService;
    private final PetService petService;
    private final PetApi petRelationService;
    private final UserInfoApi userInfoApi;
    public Profile postProfile(ProfileRequest profileRequest,UserInfo userInfo)
    {
        Profile profile = new Profile(profileRequest,userInfo);
        profile.setProfileId(profileSequenceGeneratorService.longSequenceGenerate(Profile.SEQUENCE_NAME));
        profile.getPetList().forEach(item->item.setProfileId(profile.getProfileId()));
        List<Pet> savePetList = petService.postAllPet(profile.getPetList());
        Profile result = profileRepository.save(profile);
//        sendToRelationServer(profile);
        return result;
    }

    public Profile putProfile(ProfileRequest profileRequest, UserInfo userInfo)
    {
        Profile profile = profileRepository.findById(profileRequest.getId()).orElseThrow(NullPointerException::new);
        profile.setProfileData(profileRequest);
        List<Pet> savePetList = petService.postAllPet(profile.getPetList());
        Profile result = profileRepository.save(profile);
//        sendToRelationServer(profile, userInfo);
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
                        .userId(username).build()
        ).collect(Collectors.toList());
//        userInfoApi.saveUserInfo(UserInfoDto.builder()
//                .nickname(profile.getNickname())
//                .userId(username)
//                .birth(profile.getBirth().toString())
//                .build()
//        );
//        petRelationService.savePet(petInfoDtos);
    }

    public Profile getProfile(Long profileId)
    {
        Profile result = profileRepository.findById(profileId).orElseThrow(NullPointerException::new);
        setProfileFollowAnFollowerCount(result);
        return result;
    }
    public Profile getProfile(UserInfo userInfo)
    {
        Profile result = getProfileByUserId(userInfo.getUserId());
        return result;
    }
    public Profile getProfileByUserId(Long userId)
    {
        Profile result = profileRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
        setProfileFollowAnFollowerCount(result);
        return result;
    }
    private void setProfileFollowAnFollowerCount(Profile profile)
    {
//        profile.setFollower(userInfoApi.countFollower(FollowRequest.builder().follower(profile.getUserId().toString()).build()));
//        profile.setFollow(userInfoApi.countFollowing(FollowRequest.builder().follower(profile.getUserId().toString()).build()));
    }
    public List<Profile> getFollowerList(Long profileId)
    {
        Profile profile = profileRepository.findById(profileId).orElseThrow(NullPointerException::new);

        List<Long> followerUserIds = userInfoApi.getFollowerList(
                FollowRequest.builder().follower(profile.getUserId().toString()).build()
        ).stream().map(Long::parseLong).collect(Collectors.toList());
        List<Profile> result = profileRepository.findByUserIdIn(followerUserIds);
        return result;
    }

    public List<Profile> getFollowList(Long profileId)
    {
        Profile profile = profileRepository.findById(profileId).orElseThrow(NullPointerException::new);

        List<Long> followUserIds = userInfoApi.getFollowingList(
                FollowRequest.builder().follower(profile.getUserId().toString()).build()
        ).stream().map(Long::parseLong).collect(Collectors.toList());
        List<Profile> result = profileRepository.findByUserIdIn(followUserIds);
        return result;
    }
}
