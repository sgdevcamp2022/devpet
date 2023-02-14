package com.devpet.feed.controller;

import com.devpet.feed.model.dto.CommentDto;
import com.devpet.feed.model.dto.LikePostDto;
import com.devpet.feed.model.dto.PostInfoDto;
import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.service.PostInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postinfo")
public class PostInfoController {

    private final PostInfoService postInfoService;

    @GetMapping("/test")
    public PostInfo getTest (){
        PostInfo postInfo = new PostInfo();

        postInfo.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return postInfo;

    }
    @PostMapping("")
    public PostInfoDto savePostInfo(@RequestBody PostInfoDto postInfoDto) throws Exception {
        return postInfoService.savePostInfo(postInfoDto);
    }
    @PostMapping("/like")
    public PostInfoDto likePost(@RequestBody LikePostDto likePostDto) throws Exception {
        return postInfoService.likePostInfo(likePostDto);
    }

    @PatchMapping("/like")
    public PostInfo dislikePost(@RequestBody LikePostDto likePostDto) throws Exception{
        return postInfoService.dislikePostInfo(likePostDto);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody CommentDto commentDto) throws Exception{
        return postInfoService.postComment(commentDto);
    }
}
