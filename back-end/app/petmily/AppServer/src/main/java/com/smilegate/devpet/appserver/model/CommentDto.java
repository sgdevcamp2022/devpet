package com.devpet.feed.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@RequiredArgsConstructor
/**
 * userId : 댓글을 단 계정 ID
 * postId : 댓글이 달린 포스트 ID
 * createdAt : 댓글이 달린 시간
 */
public class CommentDto {
    private String userId;
    private String postId;
    private Timestamp createdAt;
}
