package neo4j.test.feed.model.relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import neo4j.test.feed.model.entity.PetInfo;
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
