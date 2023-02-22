package com.devpet.feed.service;

import com.devpet.feed.model.dto.LikePostDto;
import com.devpet.feed.model.dto.PetInfoDto;
import com.devpet.feed.model.entity.PetInfo;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Pet;
import com.devpet.feed.repository.Neo4jRepo;
import com.devpet.feed.repository.PetRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    @Value("${spring.neo4j.uri}")
    String uri;
    @Value("${spring.neo4j.authentication.username}")
    String username;
    @Value("${spring.neo4j.authentication.password}")
    String password;
    private final static Config config = Config.defaultConfig();

    private final UserInfoRepository userRepository;
    private final PetRepository petRepository;

    @Transactional
    public ResponseEntity savePet(List<PetInfoDto> petList){
        Neo4jRepo neo4jRepository = new Neo4jRepo(uri, username, password, config);
        neo4jRepository.savePetAll(petList);
        return ResponseEntity.ok("SUCCESS");
    }


//    // 펫 노드 생성 동시에 유저와 관계 생성
//    @Transactional
//    public List<Long> savePet(List<PetInfoDto> petList) {
//
//        List<String> userIds = new ArrayList<>();
//        List<PetInfo> petInfos = new ArrayList<>();
//        for (PetInfoDto petInfoDto : petList) {
//            PetInfo petInfo = new PetInfo(petInfoDto);
//            petInfos.add(petInfo);
//            userIds.add(petInfoDto.getUserId());
//        }
//
//        // 위 반복문에서 petList 에 있는 PetInfoDto 객체로부터 생성한 PetInfo 객체를 petInfos 에 담고 saveAll 메서드를 이용해서 한번에 DB에 저장한다.
//        List<PetInfo> petInfoList = petRepository.saveAll(petInfos);
//        // findAllByUserIdIn 메서드를 사용해서 DB 에 있는 노드들의 속성 값중 인수인 userIds 값들이 존재하다면 다 가져와서 userInfoList 에 담는다.
//        List<UserInfo> userInfoList = userRepository.findAllByUserIdIn(userIds);
//        Map<String, UserInfo> usersMap = userInfoList.stream().collect(Collectors.toMap(UserInfo::getUserId, Function.identity(), (userInfo, userInfo2) -> userInfo ));
//        Map<Long, PetInfo> petsMap = petInfoList.stream().collect(Collectors.toMap(PetInfo::getPetId, Function.identity()));
//
//        List<Long> petIdList = new ArrayList<>();
//        // user 한명이 여러 펫을 동시 생성 할 수 있기에 saveAll 메서드를 사용하기 위해 user 중복을 없애는 Set 자료형을 사용하였다.
//        Set<UserInfo> users = new HashSet<>();
//        // 관계를 만들기 위해서는 2개의 노드가 필요하기에 위에 petId 와 userId 값을 키로 가진 Map 자료형을 만들어서 반복문에서 사용하여 pet, user 노드들을 생성한다.
//        for (PetInfoDto petInfoDto : petList) {
//
//            UserInfo userNode = usersMap.get(petInfoDto.getUserId());
//            PetInfo petNode = petsMap.get(petInfoDto.getPetId());
//
//            Long uid = petNode.getPetId();
//            Pet pet = new Pet(petNode);
//            userNode.getPet().add(pet);
//            users.add(userNode);
//            petIdList.add(uid);
//        }
//
//        userRepository.saveAll(users);
//        return petIdList;
//    }

    // 펫 수정
//    @Transactional
//    public void putPet(List<PetInfoDto> petList) {
//        deletePet(petList);
//        savePet(petList);
//    }

    // 펫 삭제
    @Transactional
    public void deletePet(List<PetInfoDto> petList) {

        List<Long> petIds = new ArrayList<>();
        for (PetInfoDto petInfoDto : petList) {
            petIds.add(petInfoDto.getPetId());
        }

        // DB에 저장되어 있는 삭제해야할 값을 가져옴
        List<PetInfo> petInfos =  petRepository.findAllByPetIdIn(petIds);
        petRepository.deleteAll(petInfos);
    }

//    @Transactional
//    public PetInfoDto getPet(Long petId) {
//
//        PetInfo petInfo = petRepository.findByPetId(petId).orElseThrow(RuntimeException::new);
//
//        PetInfoDto petInfoDto = PetInfoDto.builder()
//                .petId(petInfo.getPetId())
//                .petName(petInfo.getPetName())
//                .petBirth(petInfo.getPetBirth())
//                .petSpecies(petInfo.getPetSpecies())
//                .build();
//
//        return petInfoDto;
//    }
}