package com.smilegate.devpet.appserver.repository.redis;

import com.smilegate.devpet.appserver.model.Feed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRedisRepository extends CrudRepository<Feed,Long> {
}
