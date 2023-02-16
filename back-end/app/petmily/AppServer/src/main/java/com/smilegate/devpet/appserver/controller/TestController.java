package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final NewPostRedisRepository newPostRedisRepository;
    private final KafkaProducerService kafkaProducerService;
    @GetMapping("/pingpong")
    public ResponseEntity<?> pingpong(@RequestBody Map<String , String> info)
    {
        kafkaProducerService.pingpongSend(info);
        return ResponseEntity.ok("test");
    }
    @KafkaListener(topics="test",groupId = "test", autoStartup = "true")
    public void testPingpong(String message)
    {
        System.out.println(message);
    }
    @GetMapping
    public void test()
    {
//        newPostRedisRepository.save(1L, 1L);
//        for(int i=1;i<saveSize;i++)
//        {
//            newPostRedisRepository.save(1L, (long) i);
//        }
//        kafkaProducerService.pingpongSend();
    }
}
