package com.smilegate.devpet.appserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostInfo {

    private String postId;
    private String userId;
    private String postCategory;
    private String createdAt;
    private Set<Tag> tags ;
    private Set<Like> likes ;
    private Set<Comment> comments;
}
