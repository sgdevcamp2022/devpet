package com.smilegate.devpet.appserver.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetInfoDto {
    private String userId;
    private String petId;
    private String petName;
    private String petBirth;
    private String petSpecies;
    private Set<Tag> tags;
}
