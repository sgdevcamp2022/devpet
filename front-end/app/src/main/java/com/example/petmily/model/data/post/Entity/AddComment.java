package com.example.petmily.model.data.post.Entity;

public class AddComment {
    String comment;
    Long profileId;
    Long parentCommentId;

    public AddComment(String comment, Long profileId, Long parentCommentId) {
        this.comment = comment;
        this.profileId = profileId;
        this.parentCommentId = parentCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
