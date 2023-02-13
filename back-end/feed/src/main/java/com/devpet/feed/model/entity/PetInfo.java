package com.devpet.feed.model.entity;

import com.devpet.feed.model.dto.PetInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node
@Getter
@Setter
@NoArgsConstructor
public class PetInfo {

    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String petId;

    @Property
    private String petName;

    @Property
    private String petBirth;

    @Property
    private String petSpecies;

    @Builder
    public PetInfo(String petName, String petBirth, String petSpecies) {

        this.petName = petName;
        this.petBirth = petBirth;
        this.petSpecies = petSpecies;
    }
    public PetInfo(PetInfoDto dto)
    {
        if (dto.getPetId()!=null)
            this.petId = dto.getPetId();
        this.petName = dto.getPetName();
        this.petBirth = dto.getPetBirth();
        this.petSpecies = dto.getPetSpecies();
    }
}
