package com.example.petmily.model.data.post.Entity;

public class AddComment {
    String comment;
    String parentId;

    public AddComment(String comment, String parentId) {
        this.comment = comment;
        this.parentId = parentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
