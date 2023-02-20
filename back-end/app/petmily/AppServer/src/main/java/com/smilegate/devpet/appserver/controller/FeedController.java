package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.service.CommentService;
import com.smilegate.devpet.appserver.service.FavoriteService;
import com.smilegate.devpet.appserver.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
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
    @Autowired
    private FavoriteService favoriteService;


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
    public boolean postFavorite(@PathVariable long feedId, @RequestBody FavoriteRequest favoriteRequest, UserInfo userInfo) {

        return favoriteService.setFeedFavorite(feedId, favoriteRequest, userInfo);
    }

    @GetMapping("/gallery")
    public List<String> getNearBySimpleFeedList(
                                        @RequestParam(value = "longitude") Double longitude,
                                        @RequestParam("latitude") Double latitude,
                                        @RequestParam("distance") int distance,
                                        @RequestParam(value = "word",required = false) String word,
                                        @RequestParam(value = "category",required = false) Integer category,
                                        @RequestParam("start") int start,
                                        @RequestParam("size") int size)
    {
        Circle center = new Circle(longitude,latitude,distance);
        return feedService.getSimpleFeedList(word,category,center,start,size);
    }
    @GetMapping
    public List<Feed> getNearByFeedList(
                                        @RequestParam("longitude") Double longitude,
                                        @RequestParam("latitude") Double latitude,
                                        @RequestParam("distance") int distance,
                                        @RequestParam(value = "word",required = false) String word,
                                        @RequestParam(value = "category",required = false) Integer category,
                                        @RequestParam("start") int start,
                                        @RequestParam("size") int size)
    {
        Circle center = new Circle(longitude,latitude,distance);
        return feedService.getFeedList(word,category,center,start,size);
    }
    @GetMapping("/marker")
    public List<Feed> getMarkerFeedList(
                                        @RequestParam("longitude") Double longitude,
                                        @RequestParam("latitude") Double latitude,
                                        @RequestParam(value = "word",required = false) String word,
                                        @RequestParam(value = "category",required = false) Integer category,
                                        @RequestParam("start") int start,
                                        @RequestParam("size") int size)
    {
        Point center = new Point(longitude,latitude);
        return feedService.getMarkerFeedList(center,category,word,start,size);
    }
    @GetMapping("/recommend")
    public List<Feed> getReccomendFeedList(UserInfo userInfo)
    {
        return feedService.getFeedList(userInfo);
    }
    @PostMapping("/{feedId}/comment")
    public long postComment(@PathVariable("feedId") long feedId, @RequestBody CommentRequest commentRequest, UserInfo userInfo) {
        return commentService.postComment(feedId, commentRequest, userInfo).getCommentId();
    }
    @PutMapping("/{feedId}/comment")
    public long putComment(@PathVariable("feedId") long feedId, @RequestBody CommentRequest commentRequest, UserInfo userInfo) {
        return commentService.putComment(commentRequest).getCommentId();
    }
    @GetMapping("/{feedId}/comment")
    public List<Comment> getComment(@PathVariable("feedId") long feedId,@RequestParam("start") int start,@RequestParam("count") int count)
    {
        return commentService.getPostComment(feedId,start,count);
    }
}
