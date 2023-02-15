package neo4j.test.feed.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import neo4j.test.feed.model.entity.PostInfo;
import neo4j.test.feed.model.entity.Tag;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostInfoDto {
    private String postId;
    private String postCategory;
    private String userId;

    private Set<Tag> tags;
    public PostInfoDto(PostInfo postInfo){
        this.postId = postInfo.getPostId();
        this.postCategory = postInfo.getPostCategory();
        this.userId = postInfo.getUserId();
        this.tags = postInfo.getTags();
    }
}
