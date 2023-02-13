package com.smilegate.devpet.appserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final String FEED_TOPIC = "FEED";
    private final String FEED_ALERT_GROUP_ID = "SUBSCRIBER_ALERT";

    @KafkaListener(topics=FEED_TOPIC,groupId = FEED_ALERT_GROUP_ID,autoStartup = "true")
    public void listen(String message)
    {
        System.out.println(message);
        // TODO: user alert new feed
    }
}
