package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final NewPostRedisRepository newPostRedisRepository;
    private final KafkaProducerService kafkaProducerService;
    @GetMapping("pingpong")
    public Long pingpong(UserInfo info)
    {
        return info.getUserId();
    }

    @GetMapping
    public void test()
    {
//        newPostRedisRepository.save(1L, 1L);
//        for(int i=1;i<saveSize;i++)
//        {
//            newPostRedisRepository.save(1L, (long) i);
//        }
        kafkaProducerService.pingpongSend();
    }
}
