package com.smilegate.devpet.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.devpet.appserver.model.Favorite;
import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedCommentKafkaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

    private final String FEED_TOPIC = "FEED";
    private final String PET_TOPIC = "PET";
    private final String POST_INFO_TOPIC = "POST_INFO";
    private final String USER_INFO_TOPIC = "USER_INFO";
    private final String FEED_SUBSCRIBER_GROUP = "FEED_SUBSCRIBER";//group-id 동적생성으로 변경

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void feedSubscribeSend(Feed feed) {
        try {
            String message = objectMapper.writeValueAsString(feed);
            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException("don't parse feed data error");
        }
    }

    public void pingpongSend(Map<String , String> info) {
        try {
            String json = objectMapper.writeValueAsString(info);
            kafkaTemplate.send("test", "test", json);
            log.info("send 이후 {}",json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void feedFavoriteSend(List<Favorite> feedFavoriteKafkaRequestList) {
        try {
            String message = objectMapper.writeValueAsString(feedFavoriteKafkaRequestList);
            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException("don't parse favorite data error");
        }
    }
    public void feedCommentSend(Long feedId, String comment, Long userId) {
        try {
            String message = objectMapper.writeValueAsString(
                    new FeedCommentKafkaRequest(feedId, comment, userId)
            );
            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException("don't parse comment data error");
        }
    }
    public void pingpongSend(String message) {
        kafkaTemplate.send("test", message);
    }
}
