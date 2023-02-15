package com.devpet.feed.model.dto;

import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.model.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostInfoDto {
    private String postId;
    private String postCategory;
    private String userId;

    private String createdAt;
    private Set<Tag> tags;
    public PostInfoDto(PostInfo postInfo){
        this.postId = postInfo.getPostId();
        this.postCategory = postInfo.getPostCategory();
        this.userId = postInfo.getUserId();
        this.tags = postInfo.getTags();
        this.createdAt = String.valueOf(postInfo.getCreatedAt());
    }
}
