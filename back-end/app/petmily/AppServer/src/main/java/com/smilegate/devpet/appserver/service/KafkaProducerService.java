package com.smilegate.devpet.appserver.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.devpet.appserver.model.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducerService {
    private final String FEED_TOPIC = "FEED";
    private final String FEED_SUBSCRIBER_GROUP = "FEED_SUBSCRIBER";//group-id 동적생성으로 변경
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void feedSubscribeSend(Feed feed) {
        try {
            String message = objectMapper.writeValueAsString(feed);
            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
        }catch (JsonProcessingException jpe)
        {
            throw new RuntimeException("don't parse feed data error");
        }
    }
}
