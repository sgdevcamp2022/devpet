package com.smilegate.devpet.appserver.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@RedisHash("feed")
@Document(collection = "feed")
@NoArgsConstructor
@AllArgsConstructor
public class Feed extends BaseModel implements Serializable {
    private static final long serialVersionUID = -1;
    @Transient
    public static final String SEQUENCE_NAME = "feed_sequence";
    @Id
    private Long feedId;
    @Field
    private String content;
    @Field
    private Location location;
    @Field
    private ArrayList<Long> tagUsers;
    @Field
    private Long groupId;
    @Field
    private ArrayList<String> imageUrl;
    @Field
    private Long profileId;
    @Field
    private Set<Tag> hashTags;
    @Transient
    private List<Comment> comments;
    @Transient
    private boolean isFavorite;

    public Feed(Long feedId, String content, Location location, ArrayList<Long> tagUsers, Long groupId, ArrayList<String> imageUrl, ArrayList<String> hashTag,Set<Tag> hashTags) {
        this.feedId = feedId;
        this.content = content;
        this.location = location;
        this.tagUsers = tagUsers;
        this.groupId = groupId;
        this.imageUrl = imageUrl;
        this.hashTags = hashTags;
    }

    public Feed(FeedRequest feedRequest, Long profileId)
    {
        setFeedData(feedRequest,profileId);
    }

    public void setFeedData(FeedRequest feedRequest,Long profileId)
    {
        this.setFeedData(feedRequest);
        this.setProfileId(profileId);
    }
    public void setFeedData(FeedRequest feedRequest)
    {
        this.setLocation(feedRequest.getLocation());
        this.setGroupId(feedRequest.getGroupId());
        this.setImageUrl(feedRequest.getImageUrl());
        this.setTagUsers(feedRequest.getTagUsers());
        this.setContent(feedRequest.getContent());
        this.hashTags = new HashSet<>(feedRequest.getHashTag());
    }
}
