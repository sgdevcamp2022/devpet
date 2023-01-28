package com.smilegate.devpet.appserver.service;


import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedRequest;
import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.repository.FeedRepository;
import com.smilegate.devpet.appserver.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final LocationService locationService;
    private final SequenceGeneratorService sequenceGeneratorService;
    @Transactional
    public Feed postFeed(FeedRequest feedRequest, Principal userInfo)
    {
        Feed feed = new Feed(feedRequest,userInfo);
        feed.setFeedId(sequenceGeneratorService.longSequenceGenerate(Feed.SEQUENCE_NAME));
        locationService.postLocation(feed.getLocation());
        feed = feedRepository.save(feed);
        // TODO: fan-out, fan-in, fcm implements
        return feed;
    }

    @Transactional
    public Feed putFeed(FeedRequest feedRequest,Long feedId)
    {
        Feed feed = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        feed.setFeedData(feedRequest);
        if (!feed.getLocation().equals(feedRequest.getLocation()))
            locationService.postLocation(feedRequest.getLocation());
        feedRepository.save(feed);
        return feed;
    }

    @Transactional
    public Feed deleteFeed(Long feedId)
    {
        Feed feed = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        feed.setUsed(false);
        feedRepository.save(feed);
        return feed;
    }

    public boolean setFeedEmotion(long feedId,int emotion,Principal userInfo)
    {
        Feed feedO = feedRepository.findById(feedId).orElseThrow(RuntimeException::new);
        // TODO: call rest api graph server
        // TODO: 그래프 서버 응답으로 좋아요 갯수 처리
        // TODO: fcm or fan-out, fan-in to feed owner
        return true;
    }
}
