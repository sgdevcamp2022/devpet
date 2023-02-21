package com.devpet.feed.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class RedisRepository {


    public static final String KEY_FOLLOWING = "FOLLOWING";
    public static final String KEY_FOLLOWER = "FOLLOWER";

    private static final String KEY_POST = "POST";


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

    /**
     * A - [:follow] -> B
     * @param followedUser 팔로잉을 받는 유저(B)
     * @param followUser 팔로잉을 하는 유저 (A)
     */
    public void cacheFollowRelation(String followedUser, String followUser){
        redisTemplate.opsForList().rightPush(keyGenerateFollower(followedUser),followUser); // B의 팔로워를 저장하는 해시
        redisTemplate.opsForList().rightPush(keyGenerateFollowing(followUser),followedUser); // A의 팔로잉을 저장하는 해시
    }

    /**
     * postID 들을 저장하고, 30분간의 expired time 설정
     * @param userId
     * @param postList
     */
    public void cachedDuplicatedId(String userId, Set<String> postList) {
        String key = keyGeneratePost(userId);

        if(postList==null || postList.isEmpty())
            return;

        redisTemplate.opsForList().rightPushAll(key, postList);
        redisTemplate.expire(key, Duration.ofMinutes(10) );
    }

    /**
     * Redis에 저장된 키를 불러옴
     * @param userId
     * @return
     */
    public List<String> getCachedDuplicatedId(String userId) {
        String key = keyGeneratePost(userId);
        Long end = Optional.of(redisTemplate.opsForList().size(key)).orElse(0L);
        return redisTemplate.opsForList().range(key , 0 , end);

    }




    public String keyGenerateFollowing(String userId)
    {
        return String.format("%s_%s",userId,KEY_FOLLOWING);
    } //userId의 팔로잉 하는 유저들
    public String keyGenerateFollower(String userId)
    {
        return String.format("%s_%s",userId,KEY_FOLLOWER);
    }   //userId의 팔로워
    public String keyGeneratePost(String userId)
    {
        return String.format("%s_%s",userId,KEY_POST);
    }   //userId의 팔로워
}
