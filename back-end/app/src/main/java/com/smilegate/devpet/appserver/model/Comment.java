package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;

@Getter @Setter
@RequiredArgsConstructor
@RedisHash("comment")
@Document("comment")
public class Comment extends BaseModel {
    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";
    @Id
    private long commentId;
    @Field
    private long postId;
    @Field
    private long profileId;
    @Field
    private long parentCommentId;
    @Field
    private String comment;
    public Comment(CommentRequest commentRequest,long postId,long seq) {
        this.setComment(commentRequest);
        this.setPostId(postId);
        this.setCommentId(seq);
    }
    public void setComment(CommentRequest commentRequest)
    {
        if (commentRequest.getCommentId()!=null)
            this.commentId = commentRequest.getCommentId();
        this.setProfileId(commentRequest.getProfileId());
        this.setComment(commentRequest.getComment());
        this.setParentCommentId(commentRequest.getParentCommentId());
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
