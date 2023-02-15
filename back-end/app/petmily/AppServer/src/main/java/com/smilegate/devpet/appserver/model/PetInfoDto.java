package com.smilegate.devpet.appserver.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetInfoDto {

    private String petId;
    private String petName;
    private String petBirth;
    private String petSpecies;

}
