package com.example.petmily.model.data.post.remote;

public class Result {
    String createdAt;
    String updatedAt;
    int commentId;
    int postId;
    int profileId;
    String comment;
    boolean used;
    String nickname;

    public Result(String createdAt, String updatedAt, int commentId, int postId, int profileId, String comment, boolean used) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentId = commentId;
        this.postId = postId;
        this.profileId = profileId;
        this.comment = comment;
        this.used = used;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
