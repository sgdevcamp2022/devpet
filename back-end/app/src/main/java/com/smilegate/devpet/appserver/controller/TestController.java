package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.TestModel;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.TestRepository;
import com.smilegate.devpet.appserver.repository.redis.FavoriteRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;
    private final SequenceGeneratorService testSequenceGeneratorService;
    private final NewPostRedisRepository newPostRedisRepository;
    @GetMapping("pingpong")
    public Long pingpong(UserInfo info)
    {
        return info.getUserId();
    }

    @PostMapping
    @Transactional
    public void test(@RequestParam("message") String message)
    {
        Long id = testSequenceGeneratorService.longSequenceBulkGenerate(TestModel.SEQUENCE_NAME,1);
        TestModel model = new TestModel(id,message);
        newPostRedisRepository.save("skine134@naver.com",1L);
        throw new RuntimeException("test exception");
//        testRepository.save(model);
    }
}
