package com.devpet.feed.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    final RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Double> scoreCache;
    @Resource(name = "redisTemplate")
    private ValueOperations<String , String >getValue;

    public void setCachedScore(String userId, Double score) {
        scoreCache.set(userId + "_" + "Score", score);
    }

    public Double getCachedScore(String userId) {
        if(redisTemplate.hasKey(userId + "_" + "Score")){
            return Double.parseDouble(getValue.get(userId + "_" + "Score"));
        }else{
            return null;
        }
    }

}
