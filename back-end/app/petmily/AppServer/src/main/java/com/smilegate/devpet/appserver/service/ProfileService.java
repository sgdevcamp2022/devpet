package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Pet;
import com.smilegate.devpet.appserver.model.Profile;
import com.smilegate.devpet.appserver.model.ProfileRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final SequenceGeneratorService profileSequenceGeneratorService;
    private final PetService petService;
    public Profile postProfile(ProfileRequest profileRequest,UserInfo userInfo)
    {
        Profile profile = new Profile(profileRequest,userInfo);
        profile.setProfileId(profileSequenceGeneratorService.longSequenceGenerate(Profile.SEQUENCE_NAME));
        profile.getPetList().forEach(item->item.setProfileId(profile.getProfileId()));
        List<Pet> savePetList = petService.postAllPet(profile.getPetList());
        Profile result = profileRepository.save(profile);
        return result;
    }

    public Profile putProfile(ProfileRequest profileRequest)
    {
        Profile profile = profileRepository.findById(profileRequest.getId()).orElseThrow(RuntimeException::new);
        profile.setProfileData(profileRequest);
        List<Pet> savePetList = petService.postAllPet(profile.getPetList());
        Profile result = profileRepository.save(profile);
        return result;
    }

    public Profile getProfile(Long profileId)
    {
        Profile result = profileRepository.findById(profileId).orElseThrow(RuntimeException::new);
        return result;
    }
    public Profile getProfile(UserInfo userInfo)
    {
        Profile result = profileRepository.findByUserId(userInfo.getUserId()).orElseThrow(RuntimeException::new);
        return result;
    }

    public List<Profile> getFollowerList(Long profileId)
    {
        List<Long> followerUserIds = null;// TODO: 관계 서버에서 follower 정보 읽어오기
        List<Profile> result = profileRepository.findByUserIdIn(followerUserIds);
        return result;
    }

    public List<Profile> getFollowList(Long profileId)
    {
        List<Long> followUserIds = null;// TODO: 관계 서버에서 follow 정보 읽어오기
        List<Profile> result = profileRepository.findByUserIdIn(followUserIds);
        return result;
    }
}
