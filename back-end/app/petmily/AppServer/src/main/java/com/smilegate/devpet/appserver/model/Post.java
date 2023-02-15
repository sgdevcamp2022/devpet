package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private PostInfo postInfo;

    public Post(PostInfo postInfo){
        this.postInfo = postInfo;
    }
}
