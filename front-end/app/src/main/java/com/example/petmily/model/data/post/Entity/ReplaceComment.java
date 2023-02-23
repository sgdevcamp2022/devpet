package com.example.petmily.model.data.post.Entity;

public class ReplaceComment {
    String comment;
    Long profileId;

    public ReplaceComment(String comment, Long profileId) {
        this.comment = comment;
        this.profileId = profileId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommentId() {
        return profileId;
    }

    public void setCommentId(Long profileId) {
        this.profileId = profileId;
    }
}
