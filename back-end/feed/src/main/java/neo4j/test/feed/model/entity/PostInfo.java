package neo4j.test.feed.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import neo4j.test.feed.model.dto.PostInfoDto;
import neo4j.test.feed.model.relationship.Like;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
@Getter
@Setter
@RequiredArgsConstructor
public class PostInfo {

    @Id
    private String postId;
    @Property
    private String userId;
    @Property
    private String postCategory;

    @Relationship(type="tagged" , direction = OUTGOING)
    private Set<Tag> tags ;
    @Relationship(type="likes" , direction = INCOMING)
    private Set<Like> likes ;
    public PostInfo(PostInfoDto postInfoDto){
        this.postId = postInfoDto.getPostId();
        this.postCategory = postInfoDto.getPostCategory();
        this.userId = postInfoDto.getUserId();
        this.tags = postInfoDto.getTags();
    }
}
