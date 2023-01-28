package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter @Setter
@Document("comment")
public class Comment extends BaseModel {
    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";
    @Field
    private long commentId;
    @Field
    private long postId;
    @Field
    private long profileId;
    @Field
    private String comment;
    public Comment(CommentRequest commentRequest,long postId,long seq) {
        this.setComment(commentRequest);
        this.setPostId(postId);
        this.setCommentId(seq);
    }
    public void setComment(CommentRequest commentRequest)
    {
        this.setProfileId(commentRequest.getUserProfile().getProfileId());
        this.setComment(commentRequest.getComment());
    }
    public void setComment(String comment)
    {
        this.setComment(comment);
    }
}
