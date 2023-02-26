package com.example.petmily.model.data.post.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "CommentSQL")
public class CommentSQL {

    @NonNull
    @PrimaryKey
    String commentId;

    public CommentSQL(@NonNull String commentId) {
        this.commentId = commentId;
    }

    @NonNull
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(@NonNull String commentId) {
        this.commentId = commentId;
    }
}
