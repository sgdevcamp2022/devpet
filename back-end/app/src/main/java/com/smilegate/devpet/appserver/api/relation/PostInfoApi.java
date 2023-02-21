package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(contextId = "post-info",name="relation")
public interface PostInfoApi {

    // 게시글 정보 저장
    @PostMapping("/postinfo")
    public PostInfoDto savePostInfo(PostInfoDto postInfoDto);

    // 좋아요
    @PostMapping("/postinfo/like")
    public String likePost(List<LikePostDto> likePostDto);

    // 좋아요 취소
    @PostMapping("/postinfo/like")
    public String dislikePost(List<LikePostDto> likePostDto);

    // 코멘트가를 달은 내 게시글 가져오기
    @GetMapping("/postinfo/comment")
    public List<String> getCommentPost(UserInfo userInfo);

    // 코멘트 저장
    @PostMapping("/postinfo/comment")
    public PostInfo postComment(CommentRelationRequest commentRequest);
}
