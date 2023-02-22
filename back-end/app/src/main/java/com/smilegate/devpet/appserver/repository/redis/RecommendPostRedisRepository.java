package com.smilegate.devpet.appserver.repository.redis;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RecommendPostRedisRepository implements RedisSetRepository<String,Long> {
    public static final String KEY_GENERATOR = "recommend_post";
    private final SetOperations<String, String> feedIdListOperation;

    public RecommendPostRedisRepository(StringRedisTemplate stringRedisTemplate) {
        this.feedIdListOperation = stringRedisTemplate.opsForSet();
    }

    public void save(String username, Long feedId) {
        feedIdListOperation.add(keyGenerator(username), feedId.toString());
    }

    public void saveAll(String username, Collection<Long> feedIds) {
        if (feedIds == null || feedIds.isEmpty())
            return;
        feedIdListOperation.add(keyGenerator(username), feedIds.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[feedIds.size()]));
    }

    public List<Long> findById(String username, int count) {
        List<String> result = feedIdListOperation.pop(keyGenerator(username), count);
        if (result == null)
            return new ArrayList<>();
        return result.stream().map(Long::parseLong).collect(Collectors.toList());
    }
    public Long count(String username)
    {
        return feedIdListOperation.size(keyGenerator(username));
    }
    public String keyGenerator(String username) {
        return String.format("%s_%s", username, KEY_GENERATOR);
    }


}
