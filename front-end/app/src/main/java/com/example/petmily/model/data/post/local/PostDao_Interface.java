<<<<<<< HEAD
package com.example.petmily.model.data.post.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao_Interface {

    @Query("SELECT * FROM PostSQL")
    List<PostSQL> getPost();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPost(List<PostSQL> postSQLList);


=======
package com.example.petmily.model.data.post.local;public interface PostDao_Interface {
>>>>>>> ec9fdf7e880a8cb7a530320265355487b63419e6
}
