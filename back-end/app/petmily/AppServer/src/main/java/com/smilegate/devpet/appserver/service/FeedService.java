package com.smilegate.devpet.appserver.service;


import com.google.common.collect.Lists;
import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.repository.mongo.FeedRepository;
import com.smilegate.devpet.appserver.repository.redis.FeedRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.NotReadRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedRedisRepository feedRedisRepository;
    private final LocationService locationService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations mongoOperations;
    private final KafkaProducerService kafkaProducerService;
    private final NotReadRedisRepository notReadRedisRepository;

    public Feed postFeed(FeedRequest feedRequest, UserInfo userInfo)
    {
        // feed 저장
        Feed feed = new Feed(feedRequest,userInfo);
        feed.setFeedId(sequenceGeneratorService.longSequenceGenerate(Feed.SEQUENCE_NAME));
        locationService.postLocation(feed.getLocation());
        feed = feedRepository.save(feed);

        // 저장된 피드 kafka로 메세지 전송
        kafkaProducerService.feedSubscribeSend(feed);
        // TODO: fcm 구독자에게 전송
        // TODO: redis에 작성자 읽지 않은 게시글 추가
        return feed;
    }

    @Cacheable(value = "Feed", key = "#feedId", cacheManager = "getCacheManager")
    public Feed putFeed(FeedRequest feedRequest,Long feedId)
    {
        Feed feed = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        feed.setFeedData(feedRequest);
        if (!feed.getLocation().equals(feedRequest.getLocation()))
            locationService.postLocation(feedRequest.getLocation());
        feedRepository.save(feed);
        return feed;
    }

    public Feed deleteFeed(Long feedId)
    {
        Feed feed = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        feed.setUsed(false);
        feedRepository.save(feed);
        return feed;
    }

    public boolean setFeedFavorite(long feedId, FavoriteRequest feedRequest, UserInfo userInfo)
    {
        kafkaProducerService.feedFavoriteSend(feedId,feedRequest.isFavorite(),userInfo.getUserId());
        // TODO: redis에서 postId에 대해서 좋아요를 표시한 대상 리스트로 저장.
//        feedRedisRepository.
        return true;
    }
    public List<String> getSimpleFeedList(String word, int category, Circle circle, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        List<Feed> result = feedRepository.findByContentRegexAndLocationCategoryAndLocationCoordWithin(word,category,circle,pageRequest);
        return  result.stream().map((feed)->{
            if (feed.getImageUrl().size()<1)
                return null;
            return feed.getImageUrl().get(0);
        }).collect(Collectors.toList());
    }
    public List<Feed> getMarkerFeedList(Point center, int category, String word, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        Location location = new Location();
        location.setCoord(center);
        location.setCategory((long)category);
        return feedRepository.findByLocationAndContent(location,word, pageRequest);
    }
    public List<Feed> getFeedList(String word, int category, Circle circle, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        return feedRepository.findByContentRegexAndLocationCategoryAndLocationCoordWithin(word,category,circle,pageRequest);
    }

    public List<Feed> postAllFeed(List<Feed> pushList) {
        List<Location> pushLocations = pushList.stream().map(Feed::getLocation).filter(Objects::nonNull).collect(Collectors.toList());
        locationService.saveAll(pushLocations);
        List<Feed> feedStream = pushList.stream().filter(item->item.getFeedId()==null).collect(Collectors.toList());
        AtomicLong lastSeq = new AtomicLong(sequenceGeneratorService.longSequenceBulkGenerate(Feed.SEQUENCE_NAME, (int) feedStream.size()));
        feedStream.forEach(item->{
            item.setFeedId(lastSeq.get());
            lastSeq.getAndIncrement();
        });
        List<Feed> result = new ArrayList<>(mongoOperations.insert(pushList,"feed"));
        return result;
    }

    public List<Feed> getFeedList(UserInfo userInfo)
    {
        List<Long> feedIds = notReadRedisRepository.findById(userInfo.getUserId(), 20);
        // TODO: 관계 서버에서 피드 아이디 리스트 조회 20 - notReadFeedIds.size()
        // feedIds.addAll()
        return Lists.newArrayList(feedRepository.findAllById(feedIds));
    }
}
