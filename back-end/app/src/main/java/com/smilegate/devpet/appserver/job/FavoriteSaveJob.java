package com.smilegate.devpet.appserver.job;

import com.smilegate.devpet.appserver.model.Favorite;
import com.smilegate.devpet.appserver.repository.mongo.FeedRepository;
import com.smilegate.devpet.appserver.repository.redis.FavoriteRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.service.FavoriteService;
import com.smilegate.devpet.appserver.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class FavoriteSaveJob extends QuartzJobBean {
    private final RedisTemplate<String,byte[]> redisTemplate;
    private final FeedRepository feedRepository;
    private final FavoriteService favoriteService;
    private final NewPostRedisRepository newPostRedisRepository;
    private final ProfileService profileService;
    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext context){
        Set<String> keys = redisTemplate.keys("*_"+ FavoriteRedisRepository.KEY_GENERATOR);

        // 데이터가 하나도 없다면 종료
        if (keys==null || keys.isEmpty())
            return;

        log.info("--------- favorite save -------------");
        // db에 저장, kafka로 전송할 데이터 생성
        ArrayList<Favorite> favoriteArrayList = new ArrayList<>();
        for(String postKey:keys)
        {
            Long postId = Long.parseLong(postKey.replace("_"+FavoriteRedisRepository.KEY_GENERATOR,""));
            long favoriteCount = 0;


            for(Map.Entry<Object,Object> favoritePair : redisTemplate.opsForHash().entries(postKey).entrySet())
            {
                String username = (String)favoritePair.getKey();
                Boolean isFavorite = (Boolean)favoritePair.getValue();
                favoriteArrayList.add(new Favorite(postId,isFavorite,username));
                // 좋아요를 누른 사람은 피드 캐시에 해당 게시글 추가.
                if(isFavorite)
                {
                    newPostRedisRepository.save(username,postId);
                    favoriteCount++;
                }
            }

            if(favoriteCount>0)
            {
                // TODO: postId에 해당하는 작성자에게 좋아요 알람(true가 1개라도 있으면)

                feedRepository.findById(postId).ifPresent((feed)->{
                    newPostRedisRepository.save(profileService.getProfile(feed.getProfileId()).getUsername(),postId);
                });
            }
        }
        redisTemplate.delete(keys);
        // db에 bulk insert
        favoriteService.postAllFavorite(favoriteArrayList);
    }
}
