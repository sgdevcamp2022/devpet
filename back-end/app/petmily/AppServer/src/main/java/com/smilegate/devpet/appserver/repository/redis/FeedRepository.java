package com.smilegate.devpet.appserver.repository.redis;

import com.smilegate.devpet.appserver.model.Feed;
import org.springframework.data.repository.CrudRepository;

public interface FeedRepository extends CrudRepository<Feed,Long> {
}
