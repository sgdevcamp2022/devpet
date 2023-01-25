package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedRequest;
import com.smilegate.devpet.appserver.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/feeds")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @PostMapping
    public long postFeed(@RequestBody FeedRequest feedRequest, Principal userInfo)
    {
        Feed feed = feedService.postFeed(feedRequest, userInfo);
        return feed.getId();
    }

    @PutMapping("/{feedId}")
    public long putFeed(@PathVariable("userId") long feedId,@RequestBody FeedRequest feedRequest)
    {
        Feed feed = feedService.putFeed(feedRequest,feedId);
        return feed.getId();
    }

    @DeleteMapping("/{feedId}")
    public long deleteFeed(@PathVariable("userId") long feedId)
    {
        Feed feed = feedService.deleteFeed(feedId);
        return feed.getId();
    }

    @PostMapping("/{feedId}/emotion")
    public boolean postEmotion(@PathVariable long feedId,@RequestBody int emotion,Principal userInfo)
    {
        return feedService.setFeedEmotion(feedId, emotion, userInfo);
    }
}
