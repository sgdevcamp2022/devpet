package com.devpet.feed.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PetInfoDto {

    private String userId;
    private String petId;
    private String petName;
    private String petBirth;
    private String petSpecies;
    private List<String> petList;

    @Builder
    public PetInfoDto(String petId, String petName, String petBirth, String petSpecies) {
        this.petId = petId;
        this.petName = petName;
        this.petBirth = petBirth;
        this.petSpecies = petSpecies;
    }

}
