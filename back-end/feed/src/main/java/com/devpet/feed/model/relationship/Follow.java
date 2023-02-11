package com.devpet.feed.data.relationship;

import com.devpet.feed.data.node.UserInfo;
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
public class Follow {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private UserInfo userInfo;

    public Follow(UserInfo userInfo) {

        this.userInfo = userInfo;
    }


}