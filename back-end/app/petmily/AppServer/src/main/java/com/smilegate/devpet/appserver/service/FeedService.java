package com.smilegate.devpet.appserver.service;


import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedRequest;
import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
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
    private final LocationService locationService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations mongoOperations;
    private final KafkaProducerService kafkaProducerService;

    public Feed postFeed(FeedRequest feedRequest, UserInfo userInfo)
    {
        Feed feed = new Feed(feedRequest,userInfo);
        feed.setFeedId(sequenceGeneratorService.longSequenceGenerate(Feed.SEQUENCE_NAME));
        locationService.postLocation(feed.getLocation());
        feed = feedRepository.save(feed);
        kafkaProducerService.feedSubscribeSend(feed);
        // TODO: fcm implements
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

    public boolean setFeedEmotion(long feedId,int emotion,UserInfo userInfo)
    {
        Feed feedO = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        // TODO: call rest api graph server
        // TODO: 그래프 서버로 카프카 전송.
        // TODO: fcm to feed owner
        return true;
    }
    public List<String> getSimpleFeedList(Point center, long distance, int category, java.lang.String word, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        List<Feed> result = feedRepository.findByNear(center,distance,category,word,pageRequest);
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
    public List<Feed> getFeedList(Point center, long distance, int category, String word, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        return feedRepository.findByNear(center,distance,category,word,pageRequest);
    }

    public List<Feed> saveAll(List<Feed> pushList) {
        List<Location> pushLocations = pushList.stream().map(Feed::getLocation).filter(Objects::nonNull).collect(Collectors.toList());
        locationService.saveAll(pushLocations);
        Stream<Feed> feedStream = pushList.stream().filter(item->item.getFeedId()!=null);
        AtomicLong lastSeq = new AtomicLong(sequenceGeneratorService.longSequenceBulkGenerate(Feed.SEQUENCE_NAME, (int) feedStream.count()));
        feedStream.forEach(item->{
            item.setFeedId(lastSeq.get());
            lastSeq.getAndIncrement();
        });
        List<Feed> result = new ArrayList<>(mongoOperations.insert(pushList,"feed"));
        return result;
    }
}
