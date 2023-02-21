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

    /*
    * 팔로우 취소
    * followDto 의 follower 필드가 사용자 본인
    * */
    @PostMapping("/follow/cancel")
    public void cancelFollow(@RequestBody FollowDto followDto) {

        userInfoService.cancelFollow(followDto);
    }

    /*
     * 팔로워 수 전달
     * followDto 의 follower 필드가 사용자 본인
     * */
    @GetMapping("/count/follower")
    public ResponseEntity<Long> countFollower(@RequestParam String followerUsername) {
        FollowDto followDto = new FollowDto();
        followDto.setFollower(followerUsername);
        return ResponseEntity.ok(userInfoService.countFollower(followDto.getFollower()));
    }

    /*
     * 팔로잉 수 전달
     * followDto 의 follower 필드가 사용자 본인
     * */
    @GetMapping("/count/following")
    public ResponseEntity<Long> countFollowing(@RequestParam String followingUsername) {
        FollowDto followDto = new FollowDto();
        followDto.setFollower(followingUsername);
        return ResponseEntity.ok(userInfoService.countFollowing(followDto.getFollower()));
    }

    /*
     * 팔로워 리스트 전달(사용자 본인을 팔로우 하고 있는 다른 사용자들)
     * followDto 의 follower 필드가 사용자 본인
     * */
    @GetMapping("/list/follower")
    public ResponseEntity<Set<String>> getFollowerList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowerList(followDto.getFollower()));
    }
    /*
     * 팔로잉 리스트 전달(사용자 본인이 팔로우 하고 있는 다른 사용자들)
     * followDto 의 follower 필드가 사용자 본인
     * */
    @GetMapping("/list/following")
    public ResponseEntity<Set<String>> getFollowingList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowingList(followDto.getFollower()));
    }

    // 내가 팔로우 한 유저들이 작성한 게시글들 가져오기
    @GetMapping("/list/follow/post")
    public ResponseEntity<Set<String>> getFollowPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowPostList(followDto.getFollower()));
    }

    // 내가 좋아요를 누른 게시글의 tag 에 관련된 다른 게시글들 불러오기
    @GetMapping("/list/like/post")
    public ResponseEntity<Set<String>> getLikePostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getLikePostList(followDto.getFollower()));
    }

    // 내가 댓글을 쓴 게시글의 tag에 관련된 다른 게시글들 불러오기
    @GetMapping("/list/comment/post")
    public ResponseEntity<Set<String>> getCommentPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getCommentPostList(followDto.getFollower()));
    }

    // 내가 팔로우 한 유저들의 recommend 관계가 있는 게시글의 tag에 관련된 게시글들 불러오기
    @GetMapping("/list/recommend/post")
    public ResponseEntity<Set<String>> getFollowRecommendPostList(@RequestBody FollowDto followDto) {

        return ResponseEntity.ok(userInfoService.getFollowRecommendPostList(followDto.getFollower()));
    }

    // 내가 키우는 펫과 관련된 태그의 게시물
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

    // 유저가 좋아요, 댓글, 키우는 펫과 관련된 태그의 게시물(주황색 부분)
    @GetMapping("/feed1/list/post")
    public ResponseEntity<Set<String>> getPetLikeCommentPostList(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(userInfoService.getPetLikeCommentPostList(followDto.getFollower()));
    }

    // 유저가 알 수 있는 사람, 행동 기반 추천 , 팔로우한 유저의 행동 기반 추천(하늘색 부분)
    @GetMapping("/feed2/list/post")
    public ResponseEntity<Set<String>> getRecommendedFollowPostList(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(userInfoService.getRecommendedFollowPostList(followDto.getFollower()));
    }

}
