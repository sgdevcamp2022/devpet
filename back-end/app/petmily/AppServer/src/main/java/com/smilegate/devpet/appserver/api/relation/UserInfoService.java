package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.FollowDto;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.model.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@FeignClient(name = "relation", value = "user_info")
public interface UserInfoService {
    @PostMapping("")
    public void saveUserInfo(UserInfoDto userInfo);
    @PostMapping("/follow")
    public UserInfoDto followUser(FollowDto followDto);
    @PatchMapping("")
    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto);

    @PostMapping("/follow/cancel")
    public void cancelFollow(FollowDto followDto);

    @GetMapping("/count/follower")
    public Long countFollower(FollowDto followDto);
    @GetMapping("/count/following")
    public Long countFollowing(FollowDto followDto);
    @GetMapping("/list/follower")
    public Set<String> getFollowerList(FollowDto followDto);
    @GetMapping("/list/following")
    public Set<String> getFollowingList(FollowDto followDto);

    @GetMapping("/list/follow/post")
    public Set<String> getFollowPostList(FollowDto followDto);
    @GetMapping("/list/like/post")
    public Set<String> getLikePostList(FollowDto followDto);
    @GetMapping("/list/comment/post")
    public Set<String> getCommentPostList(FollowDto followDto);
    @GetMapping("/list/recommend/post")
    public Set<String> getFollowRecommendPostList(FollowDto followDto);

    @GetMapping("/list/pet/post")
    public Set<String> getPetPostList(FollowDto followDto);
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
    public Set<String> getFollowingCommentPostList(FollowDto followDto);
}
