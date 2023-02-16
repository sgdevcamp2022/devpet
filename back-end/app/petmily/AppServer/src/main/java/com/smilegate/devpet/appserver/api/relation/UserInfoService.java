package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.FollowRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.model.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@FeignClient(contextId = "user-info",name="relation")
public interface UserInfoService {
    @PostMapping("")
    public void saveUserInfo(UserInfoDto userInfo);
    @PostMapping("/follow")
    public UserInfoDto followUser(FollowRequest followRequest);
    @PatchMapping("")
    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto);

    @PostMapping("/follow/cancel")
    public void cancelFollow(FollowRequest followRequest);
    @GetMapping("/count/follower")//
    public Long countFollower(FollowRequest followRequest);
    @GetMapping("/count/following")//
    public Long countFollowing(FollowRequest followRequest);
    @GetMapping("/list/follower")//
    public Set<String> getFollowerList(FollowRequest followRequest);
    @GetMapping("/list/following")//
    public Set<String> getFollowingList(FollowRequest followRequest);

    @GetMapping("/list/follow/post")
    public Set<String> getFollowPostList(FollowRequest followRequest);
    @GetMapping("/list/like/post")
    public Set<String> getLikePostList(FollowRequest followRequest);
    @GetMapping("/list/comment/post")
    public Set<String> getCommentPostList(FollowRequest followRequest);
    @GetMapping("/list/recommend/post")
    public Set<String> getFollowRecommendPostList(FollowRequest followRequest);

    @GetMapping("/list/pet/post")
    public Set<String> getPetPostList(FollowRequest followRequest);
    /**
     * 내가 알 수 있는 사람의 게시물
     */
    @GetMapping("/list/follow/recommend/post")
    public List<String> getFollowingRecommendPostList(UserInfo userInfo);
    /**
     * 내가 팔로우 한 유저의 새로운 게시물 보기
     */
    @GetMapping("/list/follow/new")
    public List<String> getFollowUserPost(UserInfo userInfo);


    @GetMapping("/list/follow/comment/post")
    public Set<String> getFollowingCommentPostList(FollowRequest followRequest);
}
