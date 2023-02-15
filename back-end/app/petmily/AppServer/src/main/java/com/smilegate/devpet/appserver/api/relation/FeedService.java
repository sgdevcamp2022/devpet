package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.ScoreRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import org.apache.catalina.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "relation", value = "feed")
public interface FeedService {
    @GetMapping(path="/feed")
    List<String> getPostList(UserInfo userInfo);

    @PostMapping(path="/feed")
    void feedScore(List<ScoreRequest> scoreDtoList);
}