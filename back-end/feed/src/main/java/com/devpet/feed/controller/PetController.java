package com.devpet.feed.controller;

import com.devpet.feed.model.dto.PetDto;
import com.devpet.feed.model.dto.PetInfoDto;
import com.devpet.feed.model.dto.PetListDto;
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

    @PostMapping
    public ResponseEntity<List<String>> savePet(@RequestBody PetListDto petList) {

        List<String> petIdList = petService.savePet(petList);

        return ResponseEntity.ok(petIdList);
    }
    @PutMapping
    public ResponseEntity<String> putPet(@RequestBody PetListDto petList) {

        petService.putPet(petList);
        return ResponseEntity.ok("수정 성공");
    }
    @DeleteMapping
    public ResponseEntity<String> deletePet(@RequestBody PetListDto petList) {

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