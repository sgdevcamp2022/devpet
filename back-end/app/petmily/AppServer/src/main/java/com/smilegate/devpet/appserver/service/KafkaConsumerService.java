package com.smilegate.devpet.appserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final String FEED_TOPIC = "FEED";
    private final String PET_TOPIC = "PET";
    private final String POST_INFO_TOPIC = "POST_INFO";
    private final String USER_INFO_TOPIC = "USER_INFO";
    private final String FEED_ALERT_GROUP_ID = "SUBSCRIBER_ALERT";
    private final FeedService feedService;
//    @KafkaListener(topics=FEED_TOPIC)
//    public void subscriberAlert(String message)
//    {
//        System.out.println(message);
//        // TODO: user alert new feed and
//    }
//
//    @KafkaListener(topics=FEED_TOPIC)
//    public void commentAlert(String message)
//    {
//        System.out.println(message);
//        // TODO: user alert new feed and
//    }
//    @KafkaListener(topics=FEED_TOPIC)
//    public void favoriteAlert(String message)
//    {
//        System.out.println(message);
//        // TODO: user alert new feed and
//    }
    @KafkaListener(topics="test", groupId = "test", autoStartup = "true")
    public void testPingpong(String message)
    {
        System.out.println(message);
    }
}
