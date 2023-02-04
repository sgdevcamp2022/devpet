package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment,Long> {

    public Page<Comment> findByPostIdOrderByCreatedAt(Long postId, Pageable pageable);
}
