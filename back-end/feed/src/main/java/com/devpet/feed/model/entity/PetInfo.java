package com.devpet.feed.model.entity;

import com.devpet.feed.model.dto.PetInfoDto;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.List;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
@Getter
@Setter
@NoArgsConstructor
public class PetInfo {

    @Id
    private Long petId;

    @Property
    private String petName;

    @Property
    private String petBirth;

    @Property
    private String petSpecies;

    @Relationship(type="TAGD" , direction = OUTGOING)
    private List<Tag> tags ;

    @Builder
    public PetInfo(Long petId, String petName, String petBirth, String petSpecies) {
        this.petId = petId;
        this.petName = petName;
        this.petBirth = petBirth;
        this.petSpecies = petSpecies;
    }
    public PetInfo(PetInfoDto dto)
    {
//        if (dto.getPetId()!=null)
//            this.petId = dto.getPetId();
        this.petId = dto.getPetId();
        this.petName = dto.getPetName();
        this.petBirth = dto.getPetBirth();
        this.petSpecies = dto.getPetSpecies();
        this.tags = dto.getTags();
    }
}
