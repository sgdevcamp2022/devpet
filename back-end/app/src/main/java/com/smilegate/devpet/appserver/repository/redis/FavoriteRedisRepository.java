package com.smilegate.devpet.appserver.repository.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FavoriteRedisRepository{
    public static final String KEY_GENERATOR = "favorite";
    public final HashOperations<String,Long,Boolean> favoriteHashOperation;

    public FavoriteRedisRepository(RedisTemplate<String,byte[]> redisTemplate)
    {
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Boolean.class));
        this.favoriteHashOperation = redisTemplate.opsForHash();
    }

    public void save(Long postId,Long userId,Boolean isFavorite)
    {
        if(favoriteHashOperation.get(keyGenerator(postId),userId)!=null)
            favoriteHashOperation.delete(keyGenerator(postId),userId);
        else
            favoriteHashOperation.put(keyGenerator(postId),userId,isFavorite);
    }
    public void saveAll(Long postId, Map<Long,Boolean> dataMap)
    {
        favoriteHashOperation.putAll(keyGenerator(postId),dataMap);
    }
    public Map<Long,Boolean> findAllById(Long postId)
    {
        Map<Long,Boolean> result = favoriteHashOperation.entries(keyGenerator(postId));
        return result;
    }
    public String keyGenerator(Long postId)
    {
        return String.format("%s_%s",postId,KEY_GENERATOR);
    }
}
