package com.smilegate.devpet.appserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaTestProducerService {
    final String TEST_TOPIC = "TEST_TOPIC";
    private final KafkaTemplate<String,String> kafkaTemplate;
    public void send(String message)
    {
        kafkaTemplate.send(TEST_TOPIC,message);
    }
}
