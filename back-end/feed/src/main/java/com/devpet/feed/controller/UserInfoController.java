package com.devpet.feed.controller;

import com.devpet.feed.model.dto.FollowDto;
import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userinfo")
@Slf4j
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 사용자 Node 생성
     *
     * @param userInfo
     */
    @PostMapping("")
    public void saveUserInfo(@RequestBody UserInfoDto userInfo) {
        userInfoService.saveUserInfo(userInfo);
    }

    /**
     * 사용자 Follow
     *
     * @param followDto
     * @return
     * @throws Exception
     */
    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@RequestBody FollowDto followDto) throws Exception {
        userInfoService.followUser(followDto.getFollowing(), followDto.getFollower());
        return ResponseEntity.ok("");
        //TODO : 반환할거 고민해보자!
    }

    /**
     * 사용자 정보 변경
     *
     * @param userInfoDto
     * @return
     * @throws Exception
     */
    @PatchMapping("")
    public UserInfoDto patchUserInfo(@RequestBody UserInfoDto userInfoDto) throws Exception {
        userInfoDto = userInfoService.patchUserInfo(userInfoDto);
        return userInfoDto;
    }

    @PostMapping("/follow/cancel")
    public void cancelFollow(@RequestBody FollowDto followDto) {

        userInfoService.cancelFollow(followDto);
    }

    @GetMapping("/count/follower")
    public ResponseEntity<Long> countFollower(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.countFollower(followDto.getFollower()));
    }

    @GetMapping("/count/following")
    public ResponseEntity<Long> countFollowing(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.countFollowing(followDto.getFollower()));
    }

    @GetMapping("/list/follower")
    public ResponseEntity<Set<String>> getFollowerList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowerList(followDto.getFollower()));
    }

    @GetMapping("/list/following")
    public ResponseEntity<Set<String>> getFollowingList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowingList(followDto.getFollower()));
    }

    @GetMapping("/list/follow/post")
    public ResponseEntity<Set<String>> getFollowPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowPostList(followDto.getFollower()));
    }

    @GetMapping("/list/like/post")
    public ResponseEntity<Set<String>> getLikePostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getLikePostList(followDto.getFollower()));
    }

    @GetMapping("/list/comment/post")
    public ResponseEntity<Set<String>> getCommentPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getCommentPostList(followDto.getFollower()));
    }

    @GetMapping("/list/recommend/post")
    public ResponseEntity<Set<String>> getFollowRecommendPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowRecommendPostList(followDto.getFollower()));
    }

    @GetMapping("/list/pet/post")
    public ResponseEntity<Set<String>> getPetPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getPetPostList(followDto.getFollower()));
    }

    /**
     * 내가 알 수 있는 사람의 게시물
     *
     * @param userId
     * @return
     */
    @GetMapping("/list/follow/recommend/post")
    public ResponseEntity<List<String>> getFollowingRecommendPostList(@RequestBody Map<String, String> userId) {
        return ResponseEntity.ok(userInfoService.getFollowingRecommendPostList(userId.get("userId")));
    }

    /**
     * 내가 팔로우 한 유저의 새로운 게시물 보기
     *
     * @param userId
     * @return
     */
    @GetMapping("/list/follow/new")
    public ResponseEntity<List<String>> getFollowUserPost(@RequestBody Map<String, String> userId) {
        return ResponseEntity.ok(userInfoService.getFollowUserPost(userId.get("userId")));
    }

//    @GetMapping("/list/follow/comment/post")
//    public ResponseEntity<Set<String>> getFollowingCommentPostList(@RequestBody FollowDto followDto){
//        return ResponseEntity.ok(userInfoService.getFollowingCommentPostList(followDto.getFollower()));
//    }

    @GetMapping("/feed1/list/post")
    public ResponseEntity<Set<String>> getPetLikeCommentPostList(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(userInfoService.getPetLikeCommentPostList(followDto.getFollower()));
    }

    @GetMapping("/feed2/list/post")
    public ResponseEntity<Set<String>> getRecommendedFollowPostList(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(userInfoService.getRecommendedFollowPostList(followDto.getFollower()));
    }

//    @DeleteMapping("/comment")
//    public void deleteComment(@RequestBody FollowDto followDto){
//
//        return ResponseEntity.ok(userInfoService.getRecommendedFollowPostList(followDto.getFollower()));
//    }
}
