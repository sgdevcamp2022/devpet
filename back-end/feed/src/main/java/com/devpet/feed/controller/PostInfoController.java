package com.devpet.feed.controller;

import com.devpet.feed.model.dto.LikePostDto;
import com.devpet.feed.model.dto.PostInfoDto;
import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.model.entity.Tag;
import com.devpet.feed.service.PostInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postinfo")
public class PostInfoController {

    private final PostInfoService postInfoService;

    @GetMapping("/test")
    public Set<Tag> getTest (){
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setTagName("cat");
        tag2.setTagName("dog");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);
        return tags;

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

}
