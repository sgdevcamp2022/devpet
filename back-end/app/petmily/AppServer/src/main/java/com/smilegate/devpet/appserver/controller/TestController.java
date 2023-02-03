package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.service.KafkaConsumerService;
import com.smilegate.devpet.appserver.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("pingpong")
    public Long pingpong(UserInfo info)
    {
        return info.getUserId();
    }

//    @GetMapping
//    public boolean geMessage()
//    {
//        kafkaTestConsumerService.listen(kafkaTestConsumerService.);
//    }
}
