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
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postinfo")
public class PostInfoController {


    private final PostInfoService postInfoService;

    @GetMapping("/test")
    public PostInfo getTest() {
        PostInfo postInfo = new PostInfo();

        postInfo.setCreatedAt(String.valueOf(new Timestamp(System.currentTimeMillis())));
        return postInfo;

    }

    @PostMapping("")
    public PostInfoDto savePostInfo(@RequestBody PostInfoDto postInfoDto) throws Exception {
        return postInfoService.savePostInfo(postInfoDto);
    }

    /**
     * like bulk insert
     * @param likePostDto
     * @return
     * @throws Exception
     */
    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestBody List<LikePostDto> likePostDto) throws Exception {
        return postInfoService.likePostInfo(likePostDto);
    }

    /**
     * dislike bulk insert
     * @param likePostDto
     * @return
     * @throws Exception
     */
    @PutMapping("/dislike")
    public ResponseEntity<?> dislikePost(@RequestBody List<LikePostDto> likePostDto) throws Exception {
        return postInfoService.dislikePostInfo(likePostDto);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getCommentPost(@RequestBody Map<String, String> userId) {
        String user = userId.get("userId");
        return postInfoService.getCommentPost(user);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody CommentDto commentDto) throws Exception {
        return postInfoService.postComment(commentDto);
    }
}
