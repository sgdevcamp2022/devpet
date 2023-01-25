package com.smilegate.devpet.appserver.service;


import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedRequest;
import com.smilegate.devpet.appserver.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FeedService {
    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public Feed postFeed(FeedRequest feedRequest, Principal userInfo)
    {

        Feed feed = new Feed(feedRequest,userInfo);
        feed.setId(sequenceGeneratorService.longSequenceGenerate(Feed.SEQUENCE_NAME));
        feed = feedRepository.save(feed);

        // TODO: fan-out, fan-in, fcm implements
        return feed;
    }

    public Feed putFeed(FeedRequest feedRequest,Long feedId)
    {
        Optional<Feed> feedOptional = feedRepository.findById(feedId);
        AtomicReference<Feed> resultFeed = new AtomicReference<>();
        feedOptional.ifPresent((findFeed)->{
            findFeed.setFeedData(feedRequest);
            resultFeed.set(findFeed);
            feedRepository.save(findFeed);
        });

        return resultFeed.get();
    }

    public Feed deleteFeed(Long feedId)
    {
        Optional<Feed> feedOptional = feedRepository.findById(feedId);
        AtomicReference<Feed> resultFeed = new AtomicReference<>();
        feedOptional.ifPresent((findFeed)->{
            // TODO: isUsed 속성 false로 변경.
            feedRepository.save(findFeed);
        });
        return resultFeed.get();
    }

    public boolean setFeedEmotion(long feedId,int emotion,Principal userInfo)
    {
        Optional<Feed> feedOptional = feedRepository.findById(feedId);
        feedOptional.ifPresent((feed)->{
            // TODO: call rest api graph server
            // TODO: 그래프 서버 응답으로 좋아요 갯수 처리
            // TODO: fcm or fan-out, fan-in to feed owner
        });
        return true;
    }
}
