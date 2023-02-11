package com.devpet.feed.data.relationship;

import com.devpet.feed.data.node.PostInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class Recommended {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private PostInfo postInfo;

    @Property
    private double score;

    public Recommended(PostInfo postInfo) {

        this.postInfo = postInfo;
    }
}
