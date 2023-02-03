package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.CommentRequest;
import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.service.CommentService;
import com.smilegate.devpet.appserver.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedController {

    @Autowired
    private FeedService feedService;
    @Autowired
    private CommentService commentService;

    @PostMapping
    public long postFeed(@RequestBody FeedRequest feedRequest, UserInfo userInfo) {
        Feed feed = feedService.postFeed(feedRequest, userInfo);
        return feed.getFeedId();
    }

    @PutMapping("/{feedId}")
    public long putFeed(@PathVariable("feedId") long feedId, @RequestBody FeedRequest feedRequest) {
        Feed feed = feedService.putFeed(feedRequest, feedId);
        return feed.getFeedId();
    }

    @DeleteMapping("/{feedId}")
    public long deleteFeed(@PathVariable("feedId") long feedId) {
        Feed feed = feedService.deleteFeed(feedId);
        return feed.getFeedId();
    }

    @PostMapping("/{feedId}/emotion")
    public boolean postEmotion(@PathVariable long feedId, @RequestBody int emotion, UserInfo userInfo) {
        return feedService.setFeedEmotion(feedId, emotion, userInfo);
    }

    @GetMapping("/gallety")
    public List<String> getNearBySimpleFeedList(
                                        @RequestParam("longitude") Double longitude,
                                        @RequestParam("latitude") Double latitude,
                                        @RequestParam("distance") int distance,
                                        @RequestParam("word") String word,
                                        @RequestParam("category") int category,
                                        @RequestParam("start") int start,
                                        @RequestParam("size") int size)
    {
        Point center = new Point(longitude,latitude);
        return feedService.getSimpleFeedList(center,distance,category,word,start,size);
    }
    @GetMapping
    public List<Feed> getNearByFeedList(
                                        @RequestParam("longitude") Double longitude,
                                        @RequestParam("latitude") Double latitude,
                                        @RequestParam("distance") int distance,
                                        @RequestParam("word") String word,
                                        @RequestParam("category") int category,
                                        @RequestParam("start") int start,
                                        @RequestParam("size") int size)
    {
        Point center = new Point(longitude,latitude);
        return feedService.getFeedList(center,distance,category,word,start,size);
    }
    @GetMapping("/marker")
    public List<Feed> getMarkerFeedList(
                                        @RequestParam("longitude") Double longitude,
                                        @RequestParam("latitude") Double latitude,
                                        @RequestParam("word") String word,
                                        @RequestParam("category") int category,
                                        @RequestParam("start") int start,
                                        @RequestParam("size") int size)
    {
        Point center = new Point(longitude,latitude);
        return feedService.getMarkerFeedList(center,category,word,start,size);
    }

    @PostMapping("/{feedId}/comment")
    public long postComment(@PathVariable("feedId") long feedId, @RequestBody CommentRequest commentRequest, UserInfo userInfo) {
        return commentService.postComment(feedId, commentRequest, userInfo).getCommentId();
    }
    @PutMapping("/{feedId}/comment")
    public long putComment(@PathVariable("feedId") long feedId, @RequestBody CommentRequest commentRequest, UserInfo userInfo) {
        return commentService.putComment(feedId, commentRequest).getCommentId();
    }
}
