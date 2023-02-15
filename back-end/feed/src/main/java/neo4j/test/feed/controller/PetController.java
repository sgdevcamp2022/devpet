package neo4j.test.feed.controller;

import lombok.RequiredArgsConstructor;
import neo4j.test.feed.model.dto.PetDto;
import neo4j.test.feed.model.dto.PetInfoDto;
import neo4j.test.feed.service.PetInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetInfoService petInfoService;

    @PostMapping()
    public ResponseEntity<String> savePet(@RequestBody PetInfoDto petInfoDto) {

        String uuId = petInfoService.savePet(petInfoDto);

        return ResponseEntity.ok(uuId);
    }
    @PutMapping()
    public ResponseEntity<String> putPet(@RequestBody PetInfoDto petInfoDto) {

        petInfoService.putPet(petInfoDto);
        return ResponseEntity.ok("수정 성공");
    }
    @DeleteMapping()
    public ResponseEntity<String> deletePet(@RequestBody PetInfoDto petInfoDto) {

        petInfoService.deletePet(petInfoDto.getPetId());
        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/raise")
    public ResponseEntity<String> raisePet(@RequestBody PetDto petDto) {

        petInfoService.raisePet(petDto);

        return ResponseEntity.ok("관계 성립");
    }

    @PostMapping("/raise/cancel")
    public ResponseEntity<String> raisePetCancel(@RequestBody PetDto petDto) {

        petInfoService.raisePetCancel(petDto);

        return ResponseEntity.ok("관계 취소");
    }

    @GetMapping()
    public ResponseEntity<PetInfoDto> getPet(@RequestBody PetInfoDto pet) {

        PetInfoDto petInfoDto = petInfoService.getPet(pet.getPetId());

        return ResponseEntity.ok(petInfoDto);
    }

}