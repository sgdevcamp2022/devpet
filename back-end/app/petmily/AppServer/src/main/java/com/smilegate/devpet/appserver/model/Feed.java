package com.smilegate.devpet.appserver.model;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Document(collection = "feed")
public class Feed extends BaseModel {
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
    public Feed(FeedRequest feedRequest, Principal userInfo)
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
