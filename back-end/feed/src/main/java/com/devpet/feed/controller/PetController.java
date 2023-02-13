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
    @DeleteMapping
    public ResponseEntity<String> deletePet(@RequestBody PetInfoDto petInfoDto) {

        petService.deletePet(petInfoDto.getPetId());
        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/raise")
    public ResponseEntity<String> raisePet(@RequestBody PetDto petDto) throws Exception {

        petService.raisePet(petDto);

        return ResponseEntity.ok("관계 성립");
    }

    @PostMapping("/raise/cancel")
    public ResponseEntity<String> raisePetCancel(@RequestBody PetDto petDto) {

        petService.raisePetCancel(petDto);

        return ResponseEntity.ok("관계 취소");
    }

    @GetMapping()
    public ResponseEntity<PetInfoDto> getPet(@RequestBody PetInfoDto pet) {

        PetInfoDto petInfoDto = petService.getPet(pet.getPetId());

        return ResponseEntity.ok(petInfoDto);
    }


}