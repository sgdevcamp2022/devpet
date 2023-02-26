package com.smilegate.devpet.appserver.model;

import lombok.*;

import java.sql.Timestamp;


/**
 * userId : 댓글을 단 계정 ID
 * postId : 댓글이 달린 포스트 ID
 * createdAt : 댓글이 달린 시간
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRelationRequest {
    private String userId;
    private String postId;
    private long createdAt;
}
