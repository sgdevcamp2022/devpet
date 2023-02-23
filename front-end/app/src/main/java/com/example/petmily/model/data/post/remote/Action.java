package com.example.petmily.model.data.post.remote;

public class Action {

    String userName;

    String postId;

    double score;


    public Action(String username, String postId, double score) {
        this.userName = username;
        this.postId = postId;
        this.score = score;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
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
