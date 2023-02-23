package com.example.petmily.model.data.post.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CommentDao_Interface {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void commentId(String commentId);
//
//    @Query("SELECT * FROM CommentSQL WHERE commentId = :commentId")
//    String getCommentId(String commentId);
}
