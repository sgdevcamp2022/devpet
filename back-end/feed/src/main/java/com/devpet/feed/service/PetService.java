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
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserInfoRepository userRepository;
    private final PetRepository petRepository;

    @Transactional
    public String savePet(PetInfoDto petInfoDto) {

        UserInfo userInfo = userRepository.findNodeById(petInfoDto.getUserId()).orElseThrow(RuntimeException::new);
        // 수정 필요
        PetInfo petInfo = petRepository.findByPetName(petInfoDto.getPetName()).orElse(petRepository.save(new PetInfo(petInfoDto)));
        String uuid = petInfo.getPetId();
        Pet pet = new Pet(petInfo);
        userInfo.getPet().add(pet);
        userRepository.save(userInfo);
        return uuid;
    }

    @Transactional
    public void putPet(PetInfoDto petInfoDto) {

        PetInfo petInfo = petRepository.findById(petInfoDto.getPetId()).orElseThrow(RuntimeException::new);
        petInfo.setPetBirth(petInfoDto.getPetBirth());
        petInfo.setPetName(petInfoDto.getPetName());
        petInfo.setPetSpecies(petInfoDto.getPetSpecies());
        petRepository.save(petInfo);
    }

    @Transactional
    public void deletePet(String petId) {

        PetInfo petInfo = petRepository.findById(petId).orElseThrow(RuntimeException::new);
        petRepository.deletePet(petId);
    }


    @Transactional
    public void raisePetCancel(PetDto petDto) {

        UserInfo userInfo = userRepository.findNodeById(petDto.getUserId()).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findByPetId(petDto.getPetId()).orElseThrow(RuntimeException::new);

        petRepository.raisePetCancel(petDto.getUserId(), petDto.getPetId());
    }
    @Transactional
    public PetInfoDto getPet(String petId) {

        PetInfo petInfo = petRepository.findByPetId(petId).orElseThrow(RuntimeException::new);

        PetInfoDto petInfoDto = PetInfoDto.builder()
                .petId(petInfo.getPetId())
                .petName(petInfo.getPetName())
                .petBirth(petInfo.getPetBirth())
                .petSpecies(petInfo.getPetSpecies())
                .build();

        return petInfoDto;
    }
}
