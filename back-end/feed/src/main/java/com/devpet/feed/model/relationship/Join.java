package com.devpet.feed.model.relationship;

import com.devpet.feed.model.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter @Setter
public class Join {
    @Id @GeneratedValue
    private Long id;
    @TargetNode
    private UserInfo userInfo;

    public Join(UserInfo userInfo){
        this.userInfo = userInfo;
    }
}
