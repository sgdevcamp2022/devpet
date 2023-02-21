package com.devpet.feed.controller;

import com.devpet.feed.model.dto.ScoreDto;
import com.devpet.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/feed")
public class FeedController {
    @Value("${spring.neo4j.uri}")
    String uri;
    @Value("${spring.neo4j.authentication.username}")
    String username;
    @Value("${spring.neo4j.authentication.password}")
    String password;


    private final FeedService feedService;

    @GetMapping("")
    public ResponseEntity<Set<String>> getPostList(@RequestBody Map<String, String> user) {
        String userId = user.get("userId");
        return ResponseEntity.ok(feedService.getPostList(userId));
    }
    @PostMapping("")
    public ResponseEntity<?> feedScore(@RequestBody List<ScoreDto> scoreDtoList) {
        feedService.feedScore(scoreDtoList);
        return ResponseEntity.ok().build();
    }

}
