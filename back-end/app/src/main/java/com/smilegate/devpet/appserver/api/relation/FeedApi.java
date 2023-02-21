package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.ScoreRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "feed", name = "relation")
public interface FeedApi {

    @GetMapping(path="/feed")
    List<String> getPostList(@RequestParam String username);


    // 사용자 피드 점수 계산 요청
    @PostMapping(path="/feed")
    void feedScore(List<ScoreRequest> scoreDtoList);
}