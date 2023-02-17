package com.devpet.feed.controller;

import com.devpet.feed.model.dto.PetInfoDto;
import com.devpet.feed.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    // 펫 저장
    @PostMapping
    public ResponseEntity<List<Long>> savePet(@RequestBody List<PetInfoDto> petList)  {

        List<Long> petIdList = petService.savePet(petList);

        return ResponseEntity.ok(petIdList);
    }

    // 펫 수정
    @PutMapping
    public ResponseEntity<String> putPet(@RequestBody List<PetInfoDto> petList) {

        petService.putPet(petList);
        return ResponseEntity.ok("수정 성공");
    }

    // 펫 삭제
    @DeleteMapping
    public ResponseEntity<String> deletePet(@RequestBody List<PetInfoDto> petList) {

        petService.deletePet(petList);
        return ResponseEntity.ok("삭제 성공");
    }


    @GetMapping()
    public ResponseEntity<PetInfoDto> getPet(@RequestBody PetInfoDto pet) {

        PetInfoDto petInfoDto = petService.getPet(pet.getPetId());

        return ResponseEntity.ok(petInfoDto);
    }


}



//    @PostMapping("/raise/cancel")
//    public ResponseEntity<String> raisePetCancel(@RequestBody PetDto petDto) {
//
//        petService.raisePetCancel(petDto);
//
//        return ResponseEntity.ok("관계 취소");
//    }