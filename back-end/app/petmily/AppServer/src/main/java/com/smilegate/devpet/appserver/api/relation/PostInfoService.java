package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "relation", value = "post_info")
public interface PostInfoService {
    @GetMapping("/test")
    public PostInfo getTest ();
    @PostMapping("")
    public PostInfoDto savePostInfo(PostInfoDto postInfoDto);
    @PostMapping("/like")
    public PostInfoDto likePost(LikePostDto likePostDto);

    @PatchMapping("/like")
    public PostInfo dislikePost(LikePostDto likePostDto);

    @GetMapping("/comment")
    public List<String> getCommentPost(UserInfo userInfo);
    @PostMapping("/comment")
    public PostInfo postComment(CommentDto commentDto);
}
