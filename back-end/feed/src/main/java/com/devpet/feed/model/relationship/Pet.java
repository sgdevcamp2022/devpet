package com.devpet.feed.model.relationship;

import com.devpet.feed.model.entity.PetInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private PetInfo petInfo;

    public Pet(PetInfo petInfo) {

        this.petInfo = petInfo;
    }
}
