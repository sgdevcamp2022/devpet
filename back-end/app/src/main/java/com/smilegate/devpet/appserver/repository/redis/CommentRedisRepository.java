package com.smilegate.devpet.appserver.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRedisRepository implements RedisSetRepository<Long,Long>{
    public static final String KEY_GENERATOR = "comment";
    private final SetOperations<String,String> commentIdSetOperation;

    public CommentRedisRepository(StringRedisTemplate stringRedisTemplate) {
        this.commentIdSetOperation = stringRedisTemplate.opsForSet();
    }

    public void save(Long userId,Long commentId)
    {
        commentIdSetOperation.add(keyGenerator(userId),commentId.toString());
    }
    public void saveAll(Long userId, Collection<Long> commentIds)
    {
        if( commentIds==null || commentIds.isEmpty())
            return;
        commentIdSetOperation.add(keyGenerator(userId),commentIds.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[commentIds.size()]));
    }
    public List<Long> findById(Long userId, int count)
    {
        List<String> result = commentIdSetOperation.pop(keyGenerator(userId),count);
        if (result==null)
            return new ArrayList<>();
        return result.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public String keyGenerator(Long userId)
    {
        return String.format("%s_%s",userId,KEY_GENERATOR);
    }
}
