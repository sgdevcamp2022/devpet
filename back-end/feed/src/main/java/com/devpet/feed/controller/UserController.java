package com.devpet.feed.controller;

import com.devpet.feed.data.dto.FollowDto;
import com.devpet.feed.data.dto.LikeDto;
import com.devpet.feed.data.dto.UserInfoDto;
import com.devpet.feed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/follow")
    public void follow(@RequestBody FollowDto followDto) {

        userService.follow(followDto);
    }
    @PostMapping("/like")
    public void like(@RequestBody LikeDto likeDto) {

        userService.like(likeDto);
    }

    @PostMapping
    public void createUser(@RequestBody UserInfoDto userInfoDto) {

        userService.createUser(userInfoDto);
    }
    @PutMapping
    public ResponseEntity<String> putUser(@RequestBody UserInfoDto userInfoDto) {

        userService.putUser(userInfoDto);

        return ResponseEntity.ok("수정 성공");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/like/cancel")
    public void cancelLike(@RequestBody LikeDto likeDto) {

        userService.cancelLike(likeDto);
    }

    @PostMapping("/follow/cancel")
    public void cancelFollow(@RequestBody FollowDto followDto) {

        userService.cancelFollow(followDto);
    }


    @GetMapping("/count/follower/{userId}")
    public ResponseEntity<Long> countFollower(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.countFollower(userId));
    }
    @GetMapping("/count/following/{userId}")
    public ResponseEntity<Long> countFollowing(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.countFollowing(userId));
    }

    @GetMapping("/list/follower/{userId}")
    public ResponseEntity<List<String>> getFollowerList(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.getFollowerList(userId));
    }
    @GetMapping("/list/following/{userId}")
    public ResponseEntity<List<String>> getFollowingList(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.getFollowingList(userId));
    }




}