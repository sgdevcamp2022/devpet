package com.devpet.feed.model.entity;

import com.devpet.feed.model.dto.PostInfoDto;
import com.devpet.feed.model.relationship.Comment;
import com.devpet.feed.model.relationship.Like;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.sql.Timestamp;
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

    @Property
    private Timestamp createdAt;
    @Relationship(type="TAGD" , direction = OUTGOING)
    private Set<Tag> tags ;
    @Relationship(type="LIKE" , direction = INCOMING)
    private Set<Like> likes ;
    @Relationship(type="COMMENT" ,direction = INCOMING)
    private Set<Comment> comments;
    public PostInfo(PostInfoDto postInfoDto){
        this.postId = postInfoDto.getPostId();
        this.postCategory = postInfoDto.getPostCategory();
        this.userId = postInfoDto.getUserId();
        this.tags = postInfoDto.getTags();
    }
}
