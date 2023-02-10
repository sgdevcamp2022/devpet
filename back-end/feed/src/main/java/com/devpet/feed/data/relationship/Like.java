package com.devpet.feed.data.relationship;

import com.devpet.feed.data.node.PostInfo;
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
public class Like {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private PostInfo postInfo;

    public Like(PostInfo postInfo) {

        this.postInfo = postInfo;
    }
}
