package com.smilegate.devpet.appserver.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.devpet.appserver.model.Favorite;
import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.FeedCommentKafkaRequest;
import com.smilegate.devpet.appserver.model.FeedFavoriteKafkaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KafkaProducerService {
    private final String FEED_TOPIC = "FEED";
    private final String FEED_SUBSCRIBER_GROUP = "FEED_SUBSCRIBER";//group-id 동적생성으로 변경
    private final ObjectMapper objectMapper;

//    public void feedSubscribeSend(Feed feed) {
//        try {
//            String message = objectMapper.writeValueAsString(feed);
//            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
//        } catch (JsonProcessingException jpe) {
//            throw new RuntimeException("don't parse feed data error");
//        }
//    }
//    public void feedFavoriteSend(List<Favorite> feedFavoriteKafkaRequestList) {
//        try {
//            String message = objectMapper.writeValueAsString(feedFavoriteKafkaRequestList);
//            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
//        } catch (JsonProcessingException jpe) {
//            throw new RuntimeException("don't parse favorite data error");
//        }
//    }
//    public void feedCommentSend(Long feedId, String comment, Long userId) {
//        try {
//            String message = objectMapper.writeValueAsString(
//                    new FeedCommentKafkaRequest(feedId, comment, userId)
//            );
//            kafkaTemplate.send(FEED_TOPIC, FEED_SUBSCRIBER_GROUP, message);
//        } catch (JsonProcessingException jpe) {
//            throw new RuntimeException("don't parse comment data error");
//        }
//    }
//    public void pingpongSend(String message) {
//        kafkaTemplate.send("test", message);
//    }
}
