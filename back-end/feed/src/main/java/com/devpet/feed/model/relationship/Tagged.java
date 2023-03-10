package com.devpet.feed.model.relationship;

import com.devpet.feed.model.entity.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;


@RelationshipProperties
@Getter
@Setter
public class Tagged {
    @Id
    @GeneratedValue
    private Long id;
    @TargetNode
    private List<Tag> tag;

    public Tagged (List<Tag> tag){
        this.tag = tag;
    }

}
