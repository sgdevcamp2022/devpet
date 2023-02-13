package com.devpet.feed.service;

import com.devpet.feed.model.dto.FollowDto;
import com.devpet.feed.model.dto.LikeDto;
import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Follow;

import com.devpet.feed.repository.PostInfoRepository;
import com.devpet.feed.repository.UserInfoRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userRepository;
    private final PostInfoRepository postRepository;
    private UserInfo userDtoToUserInfo(UserInfoDto dto) {
        return new UserInfo(dto);
    }

    private UserInfoDto userInfoToUserInfoDto(UserInfo info) {
        return new UserInfoDto(info);
    }

    @Transactional
    public UserInfoDto saveUserInfo(UserInfoDto userInfo) {
        UserInfo save = new UserInfo(userInfo);
        UserInfo user = userRepository.findNodeById(save.getUserId()).orElse(userRepository.save(userDtoToUserInfo(userInfo)));
        return userInfoToUserInfoDto(user);
    }

    @Transactional
    public UserInfoDto followUser(String followedUser, String followUser){

        //
        UserInfo user = userRepository.findNodeById(followedUser).orElseThrow(RuntimeException::new);
        UserInfo follower = userRepository.findNodeById(followUser).orElseThrow(RuntimeException::new);

        Set<Follow> userFollower = user.getFollowers();
        Follow followerNode = new Follow(follower);
        userFollower.add(followerNode);
        return userInfoToUserInfoDto(userRepository.save(user));
    }

    @Transactional
    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto) throws Exception {
        UserInfo userInfo = userDtoToUserInfo(userInfoDto);
        if (userRepository.existsById(userInfo.getUserId())) {
            return userInfoToUserInfoDto(userRepository.save(userInfo));
        } else throw new Exception("계정이 존재하지 않습니다.");
    }

    public UserInfo patchFollower(String followedUser, String followUser) {

        return userRepository.deleteFollowById(followedUser , followUser);

    }


    /**
     * 좋아요를 취소합니다.
     * @param likeDto
     */
    public void cancelLike(LikeDto likeDto) {
        //
        userRepository.findNodeById(likeDto.getUserId()).orElseThrow(RuntimeException::new);
        postRepository.findNodeById(likeDto.getPostId()).orElseThrow(RuntimeException::new);
        userRepository.cancelLike(likeDto.getUserId(), likeDto.getPostId());

    }

    /**
     * 팔로우를 취소 합니다.
     * @param followDto
     */
    public void cancelFollow(FollowDto followDto) {

        // 팔로워가 db에 존재하는지 확인
        userRepository.findNodeById(followDto.getFollower()).orElseThrow(RuntimeException::new);
        // 팔로잉 대상이 db에 존재하는지 확인
        userRepository.findNodeById(followDto.getFollowing()).orElseThrow(RuntimeException::new);
        // 팔로잉 대상의 팔로워 연결 제거
        userRepository.cancelFollow(followDto.getFollower(), followDto.getFollowing());
    }


    /**
     * @param userId 팔로워 수를 확인할 사용자 아이디
     * @return 팔로워 수
     */
    public Long countFollower(String userId) {
        Long followerCount = userRepository.countFollower(userId);
        return followerCount;
    }

    /**
     * @param userId 팔로잉 수를 확인할 사용자 아이디
     * @return 팔로잉 수
     */
    public Long countFollowing(String userId) {
        Long followingCount = userRepository.countFollowing(userId);
        return followingCount;
    }

    public List<String> getFollowerList(String userId) {
        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getFollowerList(userId);
    }
    public List<String> getFollowingList(String userId) {
        // userId가 db에 존재하는지 확인
        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getFollowingList(userId);
    }

}
