package com.example.petmily.model.data.post.Entity;

public class ReplaceComment {
    String comment;
    int commentId;

    public ReplaceComment(String comment, int commentId) {
        this.comment = comment;
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
