package com.smilegate.devpet.appserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final String FEED_TOPIC = "FEED";
    private final String FEED_ALERT_GROUP_ID = "SUBSCRIBER_ALERT";

    @KafkaListener(topics=FEED_TOPIC,groupId = FEED_ALERT_GROUP_ID, autoStartup = "true")
    public void subscriberAlert(String message)
    {
        System.out.println(message);
        // TODO: user alert new feed and
        // TODO: 각 사용자마다 읽지 않은 게시글 목록에 postId 추가.
    }

    @KafkaListener(topics=FEED_TOPIC,groupId = FEED_ALERT_GROUP_ID, autoStartup = "true")
    public void commentAlert(String message)
    {
        System.out.println(message);
        // TODO: user alert new feed and
    }
    @KafkaListener(topics=FEED_TOPIC,groupId = FEED_ALERT_GROUP_ID, autoStartup = "true")
    public void favoriteAlert(String message)
    {
        System.out.println(message);
        // TODO: user alert new feed and
    }

}
