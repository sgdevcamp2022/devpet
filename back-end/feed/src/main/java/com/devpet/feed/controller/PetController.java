package com.devpet.feed.controller;

import com.devpet.feed.model.dto.PetDto;
import com.devpet.feed.model.dto.PetInfoDto;
import com.devpet.feed.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/test")
    public void like(@RequestBody PetDto petDto) {

        petService.pet(petDto);
    }

    @PostMapping
    public ResponseEntity<String> savePet(@RequestBody PetInfoDto petInfoDto) {

        String uuId = petService.savePet(petInfoDto);

        return ResponseEntity.ok(uuId);
    }
    @PutMapping
    public ResponseEntity<String> putPet(@RequestBody PetInfoDto petInfoDto) {

        petService.putPet(petInfoDto);
        return ResponseEntity.ok("수정 성공");
    }
    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable("petId") String petId) {

        petService.deletePet(petId);
        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/raise/{userId}/{petId}")
    public ResponseEntity<String> raisePet(@PathVariable("userId") String userId,
                                           @PathVariable("petId") String petId) {

        petService.raisePet(userId, petId);

        return ResponseEntity.ok("관계 성립");
    }
    @PostMapping("/raise/cancel/{userId}/{petId}")
    public ResponseEntity<String> raisePetCancel(@PathVariable("userId") String userId,
                                                 @PathVariable("petId") String petId) {

        petService.raisePetCancel(userId, petId);

        return ResponseEntity.ok("관계 취소");
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetInfoDto> getPet(@PathVariable("petId") String petId) {

        PetInfoDto petInfoDto = petService.getPet(petId);

        return ResponseEntity.ok(petInfoDto);
    }


}