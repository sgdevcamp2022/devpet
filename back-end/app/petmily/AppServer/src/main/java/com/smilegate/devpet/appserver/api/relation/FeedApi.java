package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.ScoreRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(contextId = "feed", name = "relation")
public interface FeedApi {

    // 추천 게시글리스트 가져오기
    @GetMapping(path="/feed")
    List<String> getPostList(UserInfo userInfo);


    // 사용자 피드 점수 계산 요청
    @PostMapping(path="/feed")
    void feedScore(List<ScoreRequest> scoreDtoList);
}