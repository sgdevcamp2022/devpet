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

        // 수정 필요
        PetInfo petInfo = petRepository.findByPetId(petInfoDto.getPetId()).orElse(petRepository.save(new PetInfo(petInfoDto)));

        String uuid = petInfo.getPetId();
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



    public void raisePet(PetDto petDto) throws Exception{

        UserInfo userInfo = userRepository.findNodeById(petDto.getUserId()).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findByPetId(petDto.getPetId()).orElseThrow(RuntimeException::new);

        PetInfo check = petRepository.checkPet(userInfo.getUserId(), petInfo.getPetId());
        if (check != null) {
            throw new Exception("이미 존재하는 관계입니다.");
        }

        Pet pet = new Pet(petInfo);
        userInfo.getPet().add(pet);

        userRepository.save(userInfo);
    }

    public void raisePetCancel(PetDto petDto) {

        UserInfo userInfo = userRepository.findNodeById(petDto.getUserId()).orElseThrow(RuntimeException::new);
        PetInfo petInfo = petRepository.findByPetId(petDto.getPetId()).orElseThrow(RuntimeException::new);

        petRepository.raisePetCancel(petDto.getUserId(), petDto.getPetId());
    }

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
