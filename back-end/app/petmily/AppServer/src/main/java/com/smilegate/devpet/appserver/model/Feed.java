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

@Getter @Setter
@Document(collection = "feed")
public class Feed {
    @Id
    private Long id;
    @Field
    private String content;
    @Field
    private Long category;
    @Field
    private Location coord;
    @Field
    private ArrayList<Long> tag;
    @Field
    private Long groupId;
    @Field
    private ArrayList<String> imageUrl;
    @Field
    private ArrayList<Long> hashTag;
    @Transient
    public static final String SEQUENCE_NAME = "feed_sequence";
    public Feed(FeedRequest feedRequest, Principal userInfo)
    {
        setFeedData(feedRequest);
    }

    public void setFeedData(FeedRequest feedRequest)
    {

    }
}
