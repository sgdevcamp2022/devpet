package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.service.KafkaTestConsumerService;
import com.smilegate.devpet.appserver.service.KafkaTestProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final KafkaTestProducerService kafkaTestProducerService;
    private final KafkaTestConsumerService kafkaTestConsumerService;
    @GetMapping("pingpong")
    public Long pingpong(UserInfo info)
    {
        return info.getUserId();
    }
    @PostMapping("/send")
    public  boolean sendMessage(@RequestBody String body)
    {
        kafkaTestProducerService.send(body);
        return true;
    }

//    @GetMapping
//    public boolean geMessage()
//    {
//        kafkaTestConsumerService.listen(kafkaTestConsumerService.);
//    }
}
