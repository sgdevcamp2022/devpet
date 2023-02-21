package com.example.petmily.model.data.post.remote;

public class Action {

    String userId;

    String postId;

    double score;


    public Action(String userId, String postId, double score) {
        this.userId = userId;
        this.postId = postId;
        this.score = score;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
