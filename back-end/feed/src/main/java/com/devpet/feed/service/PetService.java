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
}