package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@RedisHash("feed")
@Document(collection = "feed")
@RequiredArgsConstructor
public class Feed extends BaseModel {
    public static enum FEED_SEARCH_MODE {GALLERY,POST,MARKER}
    @Transient
    public static final String SEQUENCE_NAME = "feed_sequence";
    @Id
    private Long feedId;
    @Field
    private String content;
    @Field
    private Location location;
    @Field
    private ArrayList<Long> tag;
    @Field
    private Long groupId;
    @Field
    private ArrayList<String> imageUrl;
    @Field
    private ArrayList<Long> hashTag;
    @Transient
    private List<Comment> comments;

    public Feed(Long feedId, String content, Location location, ArrayList<Long> tag, Long groupId, ArrayList<String> imageUrl, ArrayList<Long> hashTag) {
        this.feedId = feedId;
        this.content = content;
        this.location = location;
        this.tag = tag;
        this.groupId = groupId;
        this.imageUrl = imageUrl;
        this.hashTag = hashTag;
    }

    public Feed(FeedRequest feedRequest, UserInfo userInfo)
    {
        setFeedData(feedRequest);
    }

    public void setFeedData(FeedRequest feedRequest)
    {
        this.setLocation(feedRequest.getLocation());
        this.setGroupId(feedRequest.getGroupId());
        this.setImageUrl(feedRequest.getImageUrl());
        this.setTag(feedRequest.getTag());
        this.setContent(feedRequest.getContent());
    }
}
