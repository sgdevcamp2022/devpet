package com.devpet.feed.model.dto;

import com.devpet.feed.model.entity.PetInfo;
import com.devpet.feed.model.entity.Tag;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PetInfoDto {

    private String userId;
    private Long petId;
    private String petName;
    private String petBirth;
    private String petSpecies;

    private List<Tag> tags;

    public PetInfoDto(PetInfo petInfo) {
        this.petId = petInfo.getPetId();
        this.petName = petInfo.getPetName();
        this.petBirth = petInfo.getPetBirth();
        this.petSpecies = petInfo.getPetSpecies();
        this.tags = petInfo.getTags();
    }

}
