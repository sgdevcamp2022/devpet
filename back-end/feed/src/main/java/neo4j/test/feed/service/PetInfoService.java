package neo4j.test.feed.service;

import lombok.RequiredArgsConstructor;
import neo4j.test.feed.exception.DataNotFoundException;
import neo4j.test.feed.exception.DuplicateUserException;
import neo4j.test.feed.model.dto.PetDto;
import neo4j.test.feed.model.dto.PetInfoDto;
import neo4j.test.feed.model.entity.PetInfo;
import neo4j.test.feed.model.entity.UserInfo;
import neo4j.test.feed.model.relationship.Pet;
import neo4j.test.feed.repository.PetRepository;
import neo4j.test.feed.repository.UserInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PetInfoService {

    private final UserInfoRepository userRepository;
    private final PetRepository petRepository;

    /// cud

    @Transactional
    public String savePet(PetInfoDto petInfoDto) {

        // 수정 필요
//        PetInfo check = petRepository.findByPetId(petInfoDto.getPetId());
//
//        if (check != null) {
//            throw new DuplicateUserException("이미 존재하는 계정입니다.");
//        }

        PetInfo petInfo = PetInfo.builder()
                .petName(petInfoDto.getPetName())
                .petBirth(petInfoDto.getPetBirth())
                .petSpecies(petInfoDto.getPetSpecies())
                .build();

        String uuId = petRepository.save(petInfo).getPetId();
        return uuId;
    }

    @Transactional
    public void putPet(@RequestBody PetInfoDto petInfoDto) {

        PetInfo petInfo = petRepository.findByPetId(petInfoDto.getPetId());
        checkExistPet(petInfo);

        petInfo.setPetBirth(petInfoDto.getPetBirth());
        petInfo.setPetName(petInfoDto.getPetName());
        petInfo.setPetSpecies(petInfoDto.getPetSpecies());
        petRepository.save(petInfo);
    }

    @Transactional
    public void deletePet(String petId) {

        PetInfo petInfo = petRepository.findByPetId(petId);
        checkExistPet(petInfo);

        petRepository.deletePet(petId);
    }

    /// cud

    @Transactional
    public void raisePet(PetDto petDto) {

        UserInfo userInfo = userRepository.findByUserId(petDto.getUserId());
        PetInfo petInfo = petRepository.findByPetId(petDto.getPetId());

        checkExistPetOrUser(userInfo, petInfo);

        PetInfo check = petRepository.checkPet(userInfo.getUserId(), petInfo.getPetId());
        if (check != null) {
            throw new DuplicateUserException("이미 존재하는 관계입니다.");
        }

//        Set<Pet> user = userInfo.getPet();
//        for (Pet entity : user) {
//            if (entity.getPetInfo().getPetId().equals(petInfo.getPetId())) {
//                throw new DuplicateUserException("이미 존재하는 계정입니다.");
//            }
//        }

        Pet pet = new Pet(petInfo);
        userInfo.getPet().add(pet);

        userRepository.save(userInfo);
    }

    @Transactional
    public void raisePetCancel(PetDto petDto) {

        UserInfo userInfo = userRepository.findByUserId(petDto.getUserId());
        PetInfo petInfo = petRepository.findByPetId(petDto.getPetId());

        checkExistPetOrUser(userInfo, petInfo);

        petRepository.raisePetCancel(petDto.getUserId(), petDto.getPetId());
    }

    @Transactional
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

//    public void raisePet(String userId, String petId) {
//
//        UserInfo userInfo = userRepository.findNodeById(userId);
//        PetInfo petInfo = petRepository.findNodeById(petId);
//
//        checkExistPetOrUser(userInfo, petInfo);
//
//        petRepository.raisePet(userId, petId);
//    }