package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment,Long> {

    public Optional<Comment> findByProfileId(Long profileId);
    public Page<Comment> findByPostId(Long postId, Pageable pageable);
    public List<Comment> findByPostId(long postId);
}
