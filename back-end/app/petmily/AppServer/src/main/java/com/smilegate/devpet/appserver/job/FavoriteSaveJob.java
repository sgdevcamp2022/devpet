package com.smilegate.devpet.appserver.job;

import com.smilegate.devpet.appserver.model.Favorite;
import com.smilegate.devpet.appserver.model.FeedFavoriteKafkaRequest;
import com.smilegate.devpet.appserver.repository.mongo.FavoriteRepository;
import com.smilegate.devpet.appserver.repository.redis.FavoriteRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.service.FavoriteService;
import com.smilegate.devpet.appserver.service.KafkaProducerService;
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
    private final KafkaProducerService kafkaProducerService;
    private final FavoriteService favoriteService;
    private final NewPostRedisRepository newPostRedisRepository;
    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("--------- favorite save -------------");
        Set<String> keys = redisTemplate.keys("*_"+ FavoriteRedisRepository.KEY_GENERATOR);

        // 데이터가 하나도 없다면 종료
        if (keys==null || keys.isEmpty())
            return;

        // db에 저장, kafka로 전송할 데이터 생성
        ArrayList<Favorite> favoriteArrayList = new ArrayList<>();
        for(String postKey:keys)
        {
            Long postId = Long.parseLong(postKey.replace("_"+FavoriteRedisRepository.KEY_GENERATOR,""));
            long favoriteCount = 0;
            for(Map.Entry<Object,Object> favoritePair : redisTemplate.opsForHash().entries(postKey).entrySet())
            {
                Long userId = (Long)favoritePair.getKey();
                Boolean isFavorite = (Boolean)favoritePair.getValue();
                favoriteArrayList.add(new Favorite(postId,isFavorite,userId));
                if(isFavorite)
                {
                    newPostRedisRepository.save(userId,postId);
                    favoriteCount++;
                }
            }
            // TODO: postId에 해당하는 작성자에게 좋아요 알람(true가 1개라도 있으면)
//            if(favoriteCount>0)
//            {
//
//            }
        }
        // db에 bulk insert
        favoriteService.postAllFavorite(favoriteArrayList);

        // kafka 전송
        kafkaProducerService.feedFavoriteSend(favoriteArrayList);
    }
}
