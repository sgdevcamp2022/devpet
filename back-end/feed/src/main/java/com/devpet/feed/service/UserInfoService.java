package com.devpet.feed.service;

import com.devpet.feed.model.dto.FollowDto;
import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Follow;

import com.devpet.feed.repository.PostInfoRepository;
import com.devpet.feed.repository.RedisRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userRepository;
    private final PostInfoRepository postRepository;
    private final RedisRepository redisRepository;
    private UserInfo userDtoToUserInfo(UserInfoDto dto) {
        return new UserInfo(dto);
    }

    private UserInfoDto userInfoToUserInfoDto(UserInfo info) {
return new UserInfoDto(info);
    }

    // 유저 노드 DB에 저장
    @Transactional
    public UserInfoDto saveUserInfo(UserInfoDto userInfo) {
        UserInfo save = new UserInfo(userInfo);
        // DB에 유저가 있는지 체크
        UserInfo user = userRepository.findNodeById(save.getUserId()).orElse(userRepository.save(userDtoToUserInfo(userInfo)));
        return userInfoToUserInfoDto(user);
    }
    // 유저 노드 수정
    @Transactional
    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto) {

        // 유저가 db에 존재하는지 확인
        userRepository.findNodeById(userInfoDto.getUserId()).orElseThrow(RuntimeException::new);
        UserInfo userInfo = userDtoToUserInfo(userInfoDto);

        return userInfoToUserInfoDto(userRepository.save(userInfo));
    }

    /*
    * followUser -[:FOLLOW]-> followedUser
    * followUser 팔로우를 하는 사람
    * followedUser 팔로우 당하는 사람
    * 
     * 사용자 팔로우 기능
     * redis 에 저장하는 기능
     * 성능 이슈 발생
     * TODO : 성능이슈 처리 _ procedure 활용?
     * @param followedUser
     * @param followUser
     * @return
     * @throws Exception
     */
//    @Transactional
//    public void followUser(String followedUser, String followUser) throws Exception{
//
//        UserInfo user = userRepository.findNodeById(followedUser).orElseThrow(RuntimeException::new);
//        UserInfo follower = userRepository.findNodeById(followUser).orElseThrow(RuntimeException::new);
//
//        // 서로 관계가 있는지 체크
//        UserInfo check = userRepository.checkFollow(followUser, followedUser);
//        if (check != null) {
//            throw new Exception("이미 존재하는 관계입니다.");
//        }
//        redisRepository.cacheFollowRelation(followedUser, followUser);
//    }

    /**
     * follow relation redis에 일괄 저장
     * @throws Exception
     */
//    @Transactional
//    public void saveFollowRelation(String followedUser, List<String> followUser){
//
//        UserInfo user = userRepository.findNodeById(followedUser).orElseThrow(RuntimeException::new);
//        Set<Follow> userFollower = new HashSet<>();
//        for (String follower: followUser) {
//
//            UserInfo followerInfo = userRepository.findNodeById(follower).orElseThrow(RuntimeException::new);
//            UserInfo check = userRepository.checkFollow(followerInfo.getUserId(), followedUser);
//            if (check != null) {
//                throw new RuntimeException("이미 존재하는 관계입니다.");
//            }
//            Follow followerNode = new Follow(followerInfo);
//            userFollower.add(followerNode);
//        }
//
//        user.setFollowers(userFollower);
//    }


    /**
     * 팔로우 또는 차단 기능
     */
    @Transactional
    public void relationship(String followedUser, String followUser, String relationship) throws Exception {

        // 팔로우 당하는 사람
        UserInfo user = userRepository.findNodeById(followedUser).orElseThrow(RuntimeException::new);
        // 팔로우를 하는 사람
        UserInfo follower = userRepository.findNodeById(followUser).orElseThrow(RuntimeException::new);

        switch(relationship) {
            case "follow" :

                // 차단관계인지 체크
                if (userRepository.checkBlock(followUser, followedUser)) {
                    throw new Exception("차단한 유저입니다.");
                }

                // 서로 관계가 있는지 체크
                UserInfo check = userRepository.checkFollow(followUser, followedUser);
                if (check != null) {
                    throw new Exception("팔로우한 유저입니다.");
                }

                Set<Follow> userFollower = user.getFollowers();
                Follow followerNode = new Follow(follower);
                userFollower.add(followerNode);
                userRepository.save(user);
                //return userInfoToUserInfoDto(userRepository.save(user));
                break;
            case "block" :

                // 차단관계인지 체크
                if (userRepository.checkBlock(followUser, followedUser)) {
                    throw new Exception("이미 차단한 유저입니다.");
                }

                userRepository.block(followUser, followedUser);
                break;
            default:
                throw new RuntimeException("Invalid request.");
        }
    }

    /**
     * 팔로우 또는 차단을 취소 합니다.
     * @param followDto
     */
    public void cancelRelationship(String relationship , FollowDto followDto) {

        // 팔로워가 db에 존재하는지 확인(사용자 본인)
        userRepository.findNodeById(followDto.getFollower()).orElseThrow(RuntimeException::new);
        // 팔로잉 대상이 db에 존재하는지 확인
        userRepository.findNodeById(followDto.getFollowing()).orElseThrow(RuntimeException::new);

        switch (relationship) {
            case "follow" :
                // 팔로잉 대상의 팔로워 연결 제거
                userRepository.cancelFollow(followDto.getFollower(), followDto.getFollowing());
                break;

            case "block" :
                // 블락 제거
                userRepository.cancelBlock(followDto.getFollower(), followDto.getFollowing());
                break;

            default:
                throw new RuntimeException("Invalid request.");
        }
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

    // 사용자 본인의 팔로워들의 postId Set으로 가져오기
    public Set<String> getFollowerList(String userId) {
        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getFollowerList(userId);
    }
    // 사용자 본인이 팔로우를 하고 있는 다른 사용자들의 postId Set으로 가져오기
    public Set<String> getFollowingList(String userId) {
        // userId가 db에 존재하는지 확인
        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getFollowingList(userId);
    }


    // 내가 팔로우 한 유저들이 작성한 게시글들 가져오기
    public Set<String> getFollowPostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getFollowPostList(userId);
    }

    // 내가 좋아요를 누른 게시글의 tag 에 관련된 다른 게시글들 불러오기
    public Set<String> getLikePostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getLikePostList(userId);
    }

    // 내가 댓글을 쓴 게시글의 tag에 관련된 다른 게시글들 불러오기
    public Set<String> getCommentPostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getCommentPostList(userId);
    }

    /*
     * 내가 팔로우 한 유저들의 recommend 관계가 있는 게시글의 tag에 관련된 게시글들 불러오기
     * */
    public Set<String> getFollowRecommendPostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getFollowRecommendPostList(userId);
    }

    // 내가 키우는 펫과 관련된 태그의 게시물
    public Set<String> getPetPostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getPetPostList(userId);
    }

    public List<String> getFollowingRecommendPostList(String userId) {
        return userRepository.getFollowingRecommendPostList(userId);
    }

    public List<String> getFollowUserPost(String userId) {
        return userRepository.getFollowingNewPostList(userId);
    }


    // 유저가 좋아요, 댓글, 키우는 펫과 관련된 태그의 게시물(주황색 부분)
    public Set<String> getPetLikeCommentPostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getPetLikeCommentPostList(userId);
    }

    // 유저가 알 수 있는 사람, 행동 기반 추천 , 팔로우한 유저의 행동 기반 추천(하늘색 부분)
    public Set<String> getRecommendedFollowPostList(String userId) {

        userRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return userRepository.getRecommendedFollowPostList(userId);
    }

}
