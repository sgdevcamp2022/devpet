package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.FollowRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.model.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(contextId = "user-info",name="relation")
public interface UserInfoApi {
    @PostMapping("/userinfo")
    public void saveUserInfo(UserInfoDto userInfo);
    @PostMapping("/userinfo/follow")
    public UserInfoDto followUser(FollowRequest followRequest);
    @PatchMapping("/userinfo")
    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto);

    @PostMapping("/userinfo/follow/cancel")
    public void cancelFollow(FollowRequest followRequest);
    @GetMapping("/userinfo/count/follower")//
    public Long countFollower(FollowRequest followRequest);
    @GetMapping("/userinfo/count/following")//
    public Long countFollowing(FollowRequest followRequest);
    @GetMapping("/userinfo/list/follower")//
    public Set<String> getFollowerList(FollowRequest followRequest);
    @GetMapping("/userinfo/list/following")//
    public Set<String> getFollowingList(FollowRequest followRequest);

    @GetMapping("/userinfo/list/follow/post")
    public Set<String> getFollowPostList(FollowRequest followRequest);
    @GetMapping("/userinfo/list/like/post")
    public Set<String> getLikePostList(FollowRequest followRequest);
    @GetMapping("/userinfo/list/comment/post")
    public Set<String> getCommentPostList(FollowRequest followRequest);
    @GetMapping("/userinfo/list/recommend/post")
    public Set<String> getFollowRecommendPostList(FollowRequest followRequest);

    @GetMapping("/userinfo/list/pet/post")
    public Set<String> getPetPostList(FollowRequest followRequest);
    /**
     * 내가 알 수 있는 사람의 게시물
     */
    @GetMapping("/userinfo/list/follow/recommend/post")
    public List<String> getFollowingRecommendPostList(UserInfo userInfo);

    /**
     * 내가 팔로우 한 유저의 새로운 게시물 보기
     * @param userInfo 사용자 정보
     * @return
     */
    @GetMapping("/userinfo/list/follow/new")
    public Set<String> getFollowUserPost(UserInfo userInfo);

    /**
     * 사용자의 관심 기반 추천
     */
    @GetMapping("/userinfo/feed1/list/post")
    public Set<String> getPetLikeCommentPostList(FollowRequest followDto);

    /**
     * 사용자의 팔로우 기반 추천
     */
    @GetMapping("/userinfo/feed2/list/post")
    public Set<String> getRecommendedFollowPostList(FollowRequest followDto);
}
