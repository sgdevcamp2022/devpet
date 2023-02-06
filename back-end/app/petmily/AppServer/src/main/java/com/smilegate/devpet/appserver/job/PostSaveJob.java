package com.smilegate.devpet.appserver.job;

import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.repository.redis.FeedRedisRepository;
import com.smilegate.devpet.appserver.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostSaveJob extends QuartzJobBean {
    private final FeedRedisRepository feedRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final FeedService feedService;

    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("--------- save post -------------");
        RedisConnection connection = getRedisConnection();
        ScanOptions options = ScanOptions.scanOptions().build();
        Cursor<byte[]> cursor = connection.scan(options);

        // 데이터가 하나도 없다면 종료
        if (!cursor.hasNext())
            return;
        Streamable<Feed> source = Streamable.of(feedRepository.findAll());
        List<Feed> pushList = source.stream().collect(Collectors.toList());
        feedService.postAllFeed(pushList);
    }

    private RedisConnection getRedisConnection()
    {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        assert connectionFactory != null;
        return connectionFactory.getConnection();
    }
}
