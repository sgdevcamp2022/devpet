package com.devpet.feed.model.relationship;

import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.sql.Timestamp;

@RelationshipProperties
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id @GeneratedValue
    private Long id;
    @TargetNode
    private UserInfo userInfo;
    @Property
    private Timestamp createdAt;

    public Comment(UserInfo userInfo){
        this.userInfo = userInfo;
    }
}
