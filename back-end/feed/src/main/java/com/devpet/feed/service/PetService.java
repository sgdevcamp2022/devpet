package com.devpet.feed.service;

import com.devpet.feed.data.dto.PetDto;
import com.devpet.feed.data.dto.PetInfoDto;
import com.devpet.feed.data.node.PetInfo;
import com.devpet.feed.data.node.UserInfo;
import com.devpet.feed.data.relationship.Pet;
import com.devpet.feed.exception.DataNotFoundException;
import com.devpet.feed.exception.DuplicateUserException;
import com.devpet.feed.repository.PetRepository;
import com.devpet.feed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public void pet(PetDto petDto) {

        UserInfo userInfo = userRepository.findById(petDto.getUserId()).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findById(petDto.getPetId()).orElseThrow(RuntimeException::new);

        Set<Pet> user = userInfo.getPet();
        for (Pet entity : user) {
            if (entity.getPetInfo().getPetId().equals(petInfo.getPetId())) {
                throw new DuplicateUserException("이미 존재하는 계정입니다.");
            }
        }

        Pet pet = new Pet(petInfo);
        userInfo.getPet().add(pet);

        userRepository.save(userInfo);

    }

    public String savePet(PetInfoDto petInfoDto) {

        // 수정 필요
        PetInfo check = petRepository.findByPetId(petInfoDto.getPetId());

        if (check != null) {
            throw new DuplicateUserException("이미 존재하는 계정입니다.");
        }

        PetInfo petInfo = PetInfo.builder()
                .petName(petInfoDto.getPetName())
                .petBirth(petInfoDto.getPetBirth())
                .petSpecies(petInfoDto.getPetSpecies())
                .build();

        String uuId = petRepository.save(petInfo).getPetId();
        return uuId;
    }
    public void putPet(@RequestBody PetInfoDto petInfoDto) {

        PetInfo petInfo = petRepository.findByPetId(petInfoDto.getPetId());
        checkExistPet(petInfo);

        petInfo.setPetBirth(petInfoDto.getPetBirth());
        petInfo.setPetName(petInfoDto.getPetName());
        petInfo.setPetSpecies(petInfoDto.getPetSpecies());
        petRepository.save(petInfo);
    }
    public void deletePet(String petId) {

        PetInfo petInfo = petRepository.findByPetId(petId);
        checkExistPet(petInfo);

        petRepository.deletePet(petId);
    }

    public void raisePet(String userId, String petId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        PetInfo petInfo = petRepository.findByPetId(petId);

        checkExistPetOrUser(userInfo, petInfo);

        petRepository.raisePet(userId, petId);
    }
    public void raisePetCancel(String userId, String petId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        PetInfo petInfo = petRepository.findByPetId(petId);

        checkExistPetOrUser(userInfo, petInfo);

        petRepository.raisePetCancel(userId, petId);
    }

    public PetInfoDto getPet(String petId) {

        PetInfo petInfo = petRepository.findByPetId(petId);
        checkExistPet(petInfo);

        PetInfoDto petInfoDto = PetInfoDto.builder()
                .petId(petInfo.getPetId())
                .petName(petInfo.getPetName())
                .petBirth(petInfo.getPetBirth())
                .petSpecies(petInfo.getPetSpecies())
                .build();

        return petInfoDto;
    }

    public void checkExistPet(PetInfo petInfo) {

        if (petInfo == null) {
            throw new DataNotFoundException("존재하지 않는 펫 입니다.");
        }
    }
    public void checkExistPetOrUser(UserInfo userInfo, PetInfo petInfo) {

        if (userInfo == null || petInfo == null) {
            throw new DataNotFoundException("존재하지 않는 계정입니다.");
        }
    }


}
