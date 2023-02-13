package com.devpet.feed.service;

import com.devpet.feed.model.dto.FollowDto;
import com.devpet.feed.model.dto.LikeDto;
import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Follow;

import com.devpet.feed.model.relationship.Like;
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
    public UserInfoDto followUser(String followedUser, String followUser) throws Exception{

        UserInfo user = userRepository.findNodeById(followedUser).orElseThrow(RuntimeException::new);
        UserInfo follower = userRepository.findNodeById(followUser).orElseThrow(RuntimeException::new);

        // 서로 관계가 있는지 체크
        UserInfo check = userRepository.checkFollow(followedUser, followUser);
        if (check != null) {
            throw new Exception("이미 존재하는 관계입니다.");
        }

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


    // 내가 팔로우 한 유저들이 작성한 게시글들 가져오기(시간순으로 정렬 과 개수 조정 필요)
    public List<String> getFollowPostList(String userId) {

        return userRepository.getFollowPostList(userId);
    }

    // 내가 좋아요를 누른 게시글의 tag 에 관련된 다른 게시글들 불러오기(시간순으로 정렬 과 개수 조정 필요)
    public List<String> getLikePostList(String userId) {

        return userRepository.getLikePostList(userId);
    }

    // 내가 댓글을 쓴 게시글의 tag에 관련된 다른 게시글들 불러오기(시간순으로 정렬 과 개수 조정 필요)
    public List<String> getCommentPostList(String userId) {

        return userRepository.getCommentPostList(userId);
    }

    /*
     * 내가 팔로우 한 유저들의 recommend 관계가 있는 게시글의 tag에 관련된 게시글들 불러오기
     * (시간순으로 정렬 과 개수 조정 필요)
     * */
    public List<String> getFollowRecommendPostList(String userId) {

        return userRepository.getFollowRecommendPostList(userId);
    }

}
