package com.devpet.feed.controller;

import com.devpet.feed.data.dto.ScoreDto;
import com.devpet.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feed/list")
    public void firstAverage(@RequestBody List<ScoreDto> scoreList) {

        feedService.firstAverage(scoreList);
    }

    @PostMapping("/feed")
    public void createRecommend(@RequestBody List<ScoreDto> scoreList) {

        feedService.createRecommend(scoreList);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<String>> getPostList(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(feedService.getPostList(userId));
    }
}
