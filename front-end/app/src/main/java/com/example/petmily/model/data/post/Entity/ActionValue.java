package com.example.petmily.model.data.post.Entity;

public class ActionValue {

    long time;
    boolean like;
    int comments;

    public ActionValue(long time, boolean like, int comments) {
        this.time = time;
        this.like = like;
        this.comments = comments;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
