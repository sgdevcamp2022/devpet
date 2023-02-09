package com.devpet.feed.dto;

import com.devpet.feed.entity.PostInfo;
import com.devpet.feed.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
