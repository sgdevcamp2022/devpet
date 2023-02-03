package com.smilegate.devpet.appserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaTestConsumerService {
    private final String TEST_TOPIC = "TEST_TOPIC";

    @KafkaListener(topics=TEST_TOPIC,groupId = "Test",autoStartup = "true")
    public void listen(String message)
    {
        System.out.println(message);
    }
}
