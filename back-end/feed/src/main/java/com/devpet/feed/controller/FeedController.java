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
//    @GetMapping("/test")
//    public Neo4jRepository test(){
//        var app = new Neo4jRepository(uri, username, password, Config.defaultConfig());
//        app.findAllUser();
//        Object result =  session.run("Match (a:UserInfo {userId: ${userId})} return a",
//                parameters("userId1"));
//
//        Object result = session.run("MATCH (a:UserInfo) WHERE a.userId = {name} " +
//                        "RETURN a.name AS name, a.title AS title",
//                parameters("name", "Arthur"));

//        var m = Cypher.node("UserInfo").named("m");
//        var statement = Cypher.match(m)
//                .returning(m)
//                .build();
//        List<UserInfo> user = (List<UserInfo>) statement;
//        System.out.println(statement.getCypher());
//        return app;
//    }


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
