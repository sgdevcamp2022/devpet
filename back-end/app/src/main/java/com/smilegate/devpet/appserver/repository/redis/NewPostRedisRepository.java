package com.smilegate.devpet.appserver.repository.redis;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NewPostRedisRepository implements RedisSetRepository<Long,Long>{
    public static final String KEY_GENERATOR = "new_post";
    private final SetOperations<String,String> feedIdListOperation;
    public NewPostRedisRepository(StringRedisTemplate stringRedisTemplate){
        this.feedIdListOperation = stringRedisTemplate.opsForSet();
    }
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
        List<String> result = feedIdListOperation.pop(keyGenerator(userId),count);
        if (result==null)
            return new ArrayList<>();
        return result.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public String keyGenerator(Long userId)
    {
        return String.format("%s_%s",userId,KEY_GENERATOR);
    }
}
