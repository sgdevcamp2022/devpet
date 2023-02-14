package com.devpet.feed.controller;

import com.devpet.feed.model.dto.ScoreDto;
import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    @GetMapping("/test")
    public List<ScoreDto> test(){
        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto scoreDto1 = new ScoreDto();
        ScoreDto scoreDto2 = new ScoreDto();
        ScoreDto scoreDto3 = new ScoreDto();

        scoreDto1.setUserId("k@gmail.com");
        scoreDto2.setUserId("a@gmail.com");
        scoreDto3.setUserId("b@gmail.com");

        scoreDto1.setPostId("ida");
        scoreDto2.setPostId("idb");
        scoreDto3.setPostId("idc");

        scoreDto1.setScore(4.5);
        scoreDto2.setScore(5);
        scoreDto3.setScore(3);
        scoreDtoList.add(scoreDto1);
        scoreDtoList.add(scoreDto2);
        scoreDtoList.add(scoreDto3);

        return scoreDtoList;

    }


    @GetMapping("")
    public ResponseEntity<List<String>> getPostList(@RequestBody Map<String, String> user) {
        String userId = user.get("userId");

        return ResponseEntity.ok(feedService.getPostList(userId));
    }
    @PostMapping("")
    public ResponseEntity<?> feedScore(@RequestBody List<ScoreDto> scoreDtoList) {
        feedService.feedScore(scoreDtoList);
        return ResponseEntity.ok().build();
    }

//
//    @GetMapping("/feed/list")
//    public void firstAverage(@RequestBody List<ScoreDto> scoreList) {
//
//        feedService.firstAverage(scoreList);
//    }
//
//    @PostMapping("/feed")
//    public void createRecommend(@RequestBody List<ScoreDto> scoreList) {
//
//        feedService.createRecommend(scoreList);
//    }
}
