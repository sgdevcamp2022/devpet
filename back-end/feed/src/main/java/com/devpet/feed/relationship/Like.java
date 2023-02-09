package com.devpet.feed.relationship;

import com.devpet.feed.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private UserInfo userInfo;

    public Like(UserInfo userInfo){
        this.userInfo = userInfo;
    }

}
