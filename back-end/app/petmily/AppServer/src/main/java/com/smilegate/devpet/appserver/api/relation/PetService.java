package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.PetDto;
import com.smilegate.devpet.appserver.model.PetInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "pet",name="relation")
public interface PetService {

    // 펫 정보 저장
    @PostMapping
    public String savePet(List<PetInfoDto> petInfoDto);

    // 펫 정보 수정
    @PutMapping
    public String putPet(List<PetInfoDto> petInfoDto);

    // 펫 정보 삭제
    @DeleteMapping
    public String deletePet(List<PetInfoDto> petInfoDto);

//    @PostMapping("/raise")
//    public String raisePet(PetDto petDto);
//
//    @PostMapping("/raise/cancel")
//    public String raisePetCancel(PetDto petDto);

    @GetMapping()
    public PetInfoDto getPet(List<PetInfoDto> pets);
}
