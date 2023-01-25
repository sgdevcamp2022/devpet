package com.smilegate.devpet.appserver.repository;


import com.smilegate.devpet.appserver.model.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends MongoRepository<Feed, Long> {
}
