package com.smilegate.devpet.appserver.model;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Principal;

@Getter @Setter
@Document(collection = "feed")
public class Feed {
    @Transient
    public static final String SEQUENCE_NAME = "feed_sequence";
    @Id
    private Long id;
    public Feed(FeedRequest feedRequest, Principal userInfo)
    {
        setFeedData(feedRequest);
    }

    public void setFeedData(FeedRequest feedRequest)
    {

    }
}
