//package com.devpet.feed.job;
//
//import com.devpet.feed.repository.RedisRepository;
//import com.devpet.feed.service.UserInfoService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//
//@Slf4j
//@RequiredArgsConstructor
//public class SaveFollowCached extends QuartzJobBean {
//
//    final RedisTemplate<String, String> redisTemplate;
//    final UserInfoService service;
//    @Override
//    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        log.info("----------start schedule---------");
//        Set<String> keys = redisTemplate.keys("*_"+ RedisRepository.KEY_FOLLOWER);
//
//        if(keys.size() == 0){
//            log.info("----------key is not exist---------");
//            return;
//        }
//
//        for(String key : keys){
//            int length = key.length();
//            Long end = Optional.of(redisTemplate.opsForList().size(key)).orElse(0L);
//            String userId = key.substring(0 , length-9);
//            List<String> followerList = redisTemplate.opsForList().range(key, 0, end);
//            try {
//                service.saveFollowRelation(userId, followerList);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
////            for (String follower: followerList) {
////                try {
////                    service.saveFollowRelation(userId, follower);
////                } catch (Exception e) {
////                    throw new RuntimeException(e);
////                }
////            }
//            redisTemplate.delete(key);
//        }
//    }
//
//}
