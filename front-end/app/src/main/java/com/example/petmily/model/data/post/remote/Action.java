package com.example.petmily.model.data.post.remote;

public class Action {

    String userId;

    String postId;

    double score;


    public Action(String username, String postId, double score) {
        this.userId = username;
        this.postId = postId;
        this.score = score;
    }

    public String getUsername() {
        return userId;
    }

    public void setUsername(String username) {
        this.userId = username;
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
