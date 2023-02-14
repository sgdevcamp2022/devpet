package com.smilegate.devpet.appserver.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CommentRedisRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private final String KEY_GENERATOR = "_comment";
    private final SetOperations<String,String> feedIdListOperation = stringRedisTemplate.opsForSet();
    public void save(Long userId,Long feedId)
    {
        feedIdListOperation.add(keyGenerator(userId),feedId.toString());
    }
    public void saveAll(Long userId, Collection<Long> feedIds)
    {
        feedIdListOperation.union(keyGenerator(userId),feedIds.stream().map(Object::toString).collect(Collectors.toList()));
    }
    public List<Long> findById(Long userId, int count)
    {
        return feedIdListOperation.pop(keyGenerator(userId),count).stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public String keyGenerator(Long userId)
    {
        return userId+KEY_GENERATOR;
    }
}
