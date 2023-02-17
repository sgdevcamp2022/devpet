package com.devpet.feed.service;

import com.devpet.feed.model.dto.PetInfoDto;
import com.devpet.feed.model.entity.PetInfo;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Pet;
import com.devpet.feed.repository.PetRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserInfoRepository userRepository;
    private final PetRepository petRepository;

    // 펫 노드 생성 동시에 유저와 관계 생성
    @Transactional
    public List<Long> savePet(List<PetInfoDto> petList) {

        List<Long> petIdList = new ArrayList<>();
        for (PetInfoDto petInfoDto : petList) {

            UserInfo userInfo = userRepository.findNodeById(petInfoDto.getUserId()).orElseThrow(RuntimeException::new);
            PetInfo petInfo = petRepository.findByPetId(petInfoDto.getPetId()).orElse(petRepository.save(new PetInfo(petInfoDto)));

            Long uid = petInfo.getPetId();
            Pet pet = new Pet(petInfo);
            userInfo.getPet().add(pet);
            userRepository.save(userInfo);
            petIdList.add(uid);
        }

        return petIdList;
    }

    // 펫 수정
    @Transactional
    public void putPet(List<PetInfoDto> petList) {

        for (PetInfoDto petInfoDto : petList) {

            PetInfo petInfo = petRepository.findByPetId(petInfoDto.getPetId()).orElseThrow(RuntimeException::new);

            petInfo.setPetBirth(petInfoDto.getPetBirth());
            petInfo.setPetName(petInfoDto.getPetName());
            petInfo.setPetSpecies(petInfoDto.getPetSpecies());
            petRepository.save(petInfo);
        }
    }

    // 펫 삭제
    @Transactional
    public void deletePet(List<PetInfoDto> petList) {

        List<String> petIdList = new ArrayList<>();
        for (PetInfoDto petInfoDto : petList) {

            PetInfo petInfo = petRepository.findByPetId(petInfoDto.getPetId()).orElseThrow(RuntimeException::new);
            petRepository.deletePet(petInfo.getPetId());
        }
    }

    @Transactional
    public PetInfoDto getPet(Long petId) {

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


//    @Transactional
//    public void raisePetCancel(PetDto petDto) {
//
//        UserInfo userInfo = userRepository.findNodeById(petDto.getUserId()).orElseThrow(RuntimeException::new);
//        PetInfo petInfo = petRepository.findByPetId(petDto.getPetId()).orElseThrow(RuntimeException::new);
//
//        petRepository.raisePetCancel(petDto.getUserId(), petDto.getPetId());
//    }