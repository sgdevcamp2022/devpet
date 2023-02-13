package com.devpet.feed.service;

import com.devpet.feed.model.dto.PetDto;
import com.devpet.feed.model.dto.PetInfoDto;
import com.devpet.feed.model.entity.PetInfo;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Pet;
import com.devpet.feed.repository.PetRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserInfoRepository userRepository;
    private final PetRepository petRepository;

    public void pet(PetDto petDto) {

        UserInfo userInfo = userRepository.findNodeById(petDto.getUserId()).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findById(petDto.getPetId()).orElseThrow(RuntimeException::new);

        Set<Pet> user = userInfo.getPet();

        Pet pet = new Pet(petInfo);
        userInfo.getPet().add(pet);

        userRepository.save(userInfo);

    }

    public String savePet(PetInfoDto petInfoDto) {

        // 수정 필요
        PetInfo petInfo = petRepository.findById(petInfoDto.getPetId()).orElse(
                petRepository.save(new PetInfo(petInfoDto))
        );

        String uuid = petInfo.getPetId();
        return uuid;
    }
    public void putPet(PetInfoDto petInfoDto) {

        PetInfo petInfo = petRepository.findById(petInfoDto.getPetId()).orElseThrow(RuntimeException::new);
        petInfo.setPetBirth(petInfoDto.getPetBirth());
        petInfo.setPetName(petInfoDto.getPetName());
        petInfo.setPetSpecies(petInfoDto.getPetSpecies());
        petRepository.save(petInfo);
    }
    public void deletePet(String petId) {

        PetInfo petInfo = petRepository.findById(petId).orElseThrow(RuntimeException::new);
        petRepository.deletePet(petId);
    }

    public void raisePet(String userId, String petId) {

        UserInfo userInfo = userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findById(petId).orElseThrow(RuntimeException::new);

        petRepository.raisePet(userId, petId);
    }
    public void raisePetCancel(String userId, String petId) {

        UserInfo userInfo = userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findById(petId).orElseThrow(RuntimeException::new);
        petRepository.raisePetCancel(userId, petId);
    }

    public PetInfoDto getPet(String petId) {

        PetInfo petInfo = petRepository.findById(petId).orElseThrow(RuntimeException::new);
        PetInfoDto petInfoDto = PetInfoDto.builder()
                .petId(petInfo.getPetId())
                .petName(petInfo.getPetName())
                .petBirth(petInfo.getPetBirth())
                .petSpecies(petInfo.getPetSpecies())
                .build();

        return petInfoDto;
    }
}
