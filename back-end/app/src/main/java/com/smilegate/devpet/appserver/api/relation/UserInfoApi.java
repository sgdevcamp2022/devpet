package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.FollowRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.model.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(contextId = "user-info",name="relation")
public interface UserInfoApi {
    @PostMapping("/userinfo")
    public void saveUserInfo(UserInfoDto userInfo);
    @PostMapping("/userinfo/follow")
    public UserInfoDto followUser(FollowRequest followRequest);
    @PutMapping("/userinfo")
    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto);

    @PostMapping("/userinfo/follow/cancel")
    public void cancelFollow(FollowRequest followRequest);
    @GetMapping(  "/userinfo/count/follower") //
    public Long countFollower(@RequestParam String followerUsername);
    @GetMapping( "/userinfo/count/following") //
    public Long countFollowing(@RequestParam String followingUsername);
    @GetMapping("/userinfo/list/follower")//
    public Set<String> getFollowerList(@RequestParam("followerId") String username);
    @GetMapping("/userinfo/list/following")//
    public Set<String> getFollowingList(@RequestParam("followId") String username);

    @GetMapping("/userinfo/list/follow/post")
    public Set<String> getFollowPostList(@RequestParam("userId") String username);
    @GetMapping("/userinfo/list/like/post")
    public Set<String> getLikePostList(@RequestParam("userId") String username);
    @GetMapping("/userinfo/list/comment/post")
    public Set<String> getCommentPostList(@RequestParam("userId") String username);
    @GetMapping("/userinfo/list/recommend/post")
    public Set<String> getFollowRecommendPostList(@RequestParam("userId") String username);

    @GetMapping("/userinfo/list/pet/post")
    public Set<String> getPetPostList(@RequestParam("userId") String username);
    /**
     * ?????? ??? ??? ?????? ????????? ?????????
     */
    @GetMapping("/userinfo/list/follow/recommend/post")
    public List<String> getFollowingRecommendPostList(@RequestParam("userId") String username);

    /**
     * ?????? ????????? ??? ????????? ????????? ????????? ??????
     * @param followUsername ????????? ?????????
     * @return
     */
    @GetMapping("/userinfo/list/follow/new")
    public Set<String> getFollowUserPost(@RequestParam("userId") String followUsername);

    /**
     * ???????????? ?????? ?????? ??????
     */
    @GetMapping("/userinfo/feed1/list/post")
    public Set<String> getPetLikeCommentPostList(@RequestParam("userId") String username);

    /**
     * ???????????? ????????? ?????? ??????
     */
    @GetMapping("/userinfo/feed2/list/post")
    public Set<String> getRecommendedFollowPostList(@RequestParam("userId") String username);
}
