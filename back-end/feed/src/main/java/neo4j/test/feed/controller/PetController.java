package neo4j.test.feed.controller;

import lombok.RequiredArgsConstructor;
import neo4j.test.feed.model.dto.PetDto;
import neo4j.test.feed.model.dto.PetInfoDto;
import neo4j.test.feed.service.PetService;
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
    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable("petId") String petId) {

        petService.deletePet(petId);
        return ResponseEntity.ok("삭제 성공");
    }


    @PostMapping("/raise")
    public ResponseEntity<String> pet(@RequestBody PetDto petDto) {

        petService.pet(petDto);

        return ResponseEntity.ok("관계 성립");
    }
//    @PostMapping("/raise/{userId}/{petId}")
//    public ResponseEntity<String> raisePet(@PathVariable("userId") String userId,
//                                           @PathVariable("petId") String petId) {
//
//        petService.raisePet(userId, petId);
//
//        return ResponseEntity.ok("관계 성립");
//    }

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