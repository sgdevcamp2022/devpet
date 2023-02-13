package com.devpet.feed.controller;

import com.devpet.feed.model.dto.FollowDto;
import com.devpet.feed.model.dto.FollowMemberDto;
import com.devpet.feed.model.dto.LikeDto;
import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param userInfo
     */
    @PostMapping("")
    public void saveUserInfo(@RequestBody UserInfoDto userInfo)  {
      userInfoService.saveUserInfo(userInfo);
    }

    /**
     * 사용자 Follow
     * @param followMemberDto
     * @return
     * @throws Exception
     */
    @PostMapping("/follow")
    public UserInfoDto followUser(@RequestBody FollowMemberDto followMemberDto)throws Exception{
        return userInfoService.followUser(followMemberDto.getUsername(), followMemberDto.getFollowUser());
    }

    /**
     * 사용자 정보 변경
     * @param userInfoDto
     * @return
     * @throws Exception
     */
    @PatchMapping("")
    public UserInfoDto patchUserInfo(@RequestBody UserInfoDto userInfoDto) throws Exception {
        userInfoDto = userInfoService.patchUserInfo(userInfoDto);
        return userInfoDto;
    }

    @PatchMapping("/follow")
    public UserInfo patchUserFollower(@RequestBody FollowMemberDto followMemberDto) throws Exception {
        UserInfo userInfoDto = userInfoService.patchFollower(followMemberDto.getUsername(), followMemberDto.getFollowUser());
        return userInfoDto;
    }

    @PostMapping("/follow/cancel")
    public void cancelFollow(@RequestBody FollowDto followDto) {

        userInfoService.cancelFollow(followDto);
    }

    @PostMapping("/like/cancel")
    public void cancelLike(@RequestBody LikeDto likeDto) {

        userInfoService.cancelLike(likeDto);
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
    public ResponseEntity<List<String>> getFollowerList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowerList(followDto.getFollower()));
    }
    @GetMapping("/list/following")
    public ResponseEntity<List<String>> getFollowingList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowingList(followDto.getFollower()));
    }

    @GetMapping("/list/follow/post")
    public ResponseEntity<List<String>> getFollowPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowPostList(followDto.getFollower()));
    }
    @GetMapping("/list/like/post")
    public ResponseEntity<List<String>> getLikePostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getLikePostList(followDto.getFollower()));
    }
    @GetMapping("/list/comment/post")
    public ResponseEntity<List<String>> getCommentPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getCommentPostList(followDto.getFollower()));
    }
    @GetMapping("/list/recommend/post")
    public ResponseEntity<List<String>> getFollowRecommendPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowRecommendPostList(followDto.getFollower()));
    }

}
