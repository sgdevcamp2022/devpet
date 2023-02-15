package neo4j.test.feed.model.relationship;

import lombok.Getter;
import lombok.Setter;
import neo4j.test.feed.model.entity.PostInfo;
import neo4j.test.feed.model.entity.UserInfo;
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

    @TargetNode
    private PostInfo postInfo;

    public Like(PostInfo postInfo) {

        this.postInfo = postInfo;
    }
}
