package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.PetInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "pet",name="relation")
public interface PetApi {

    // 펫 정보 저장
    @PostMapping("/pet")
    public String savePet(List<PetInfoDto> petInfoDto);

    // 펫 정보 수정
    @PutMapping("/pet")
    public String putPet(List<PetInfoDto> petInfoDto);

    // 펫 정보 삭제
    @DeleteMapping("/pet")
    public String deletePet(List<PetInfoDto> petInfoDto);

//    @PostMapping("/raise")
//    public String raisePet(PetDto petDto);
//
//    @PostMapping("/raise/cancel")
//    public String raisePetCancel(PetDto petDto);

    @GetMapping("/pet")
    public PetInfoDto getPet(List<PetInfoDto> pets);
}
