package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.PetDto;
import com.smilegate.devpet.appserver.model.PetInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface PetService {
    @PostMapping
    public String savePet(PetInfoDto petInfoDto);
    @PutMapping
    public String putPet(PetInfoDto petInfoDto);
    @DeleteMapping
    public String deletePet(PetInfoDto petInfoDto);

    @PostMapping("/raise")
    public String raisePet(PetDto petDto);

    @PostMapping("/raise/cancel")
    public String raisePetCancel(PetDto petDto);

    @GetMapping()
    public PetInfoDto getPet(PetInfoDto pet);
}
