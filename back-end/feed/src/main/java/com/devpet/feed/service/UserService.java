package com.devpet.feed.service;

import com.devpet.feed.data.dto.FollowDto;
import com.devpet.feed.data.dto.LikeDto;
import com.devpet.feed.data.dto.UserInfoDto;
import com.devpet.feed.data.node.PostInfo;
import com.devpet.feed.data.node.UserInfo;
import com.devpet.feed.data.relationship.Follow;
import com.devpet.feed.data.relationship.Like;
import com.devpet.feed.exception.DataNotFoundException;
import com.devpet.feed.exception.DuplicateUserException;
import com.devpet.feed.repository.PostRepository;
import com.devpet.feed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public void follow(FollowDto followDto) {

        UserInfo following = userRepository.findByUserId(followDto.getFollowing());
        UserInfo follower = userRepository.findByUserId(followDto.getFollower());

        Set<Follow> userFollower = following.getFollower();
        for (Follow entity : userFollower) {
            if (entity.getUserInfo().getUserId().equals(follower.getUserId())) {
                throw new DuplicateUserException("이미 존재하는 계정입니다.");
            }
        }

        Follow followerNode = new Follow(follower);
        following.getFollower().add(followerNode);

        userRepository.save(following);
    }
    public void like(LikeDto likeDto) {

        UserInfo userInfo = userRepository.findById(likeDto.getUserId()).orElseThrow(RuntimeException::new);
        PostInfo postInfo = postRepository.findById(likeDto.getPostId()).orElseThrow(RuntimeException::new);

        Set<Like> user = userInfo.getLike();
        for (Like entity : user) {
            if (entity.getPostInfo().getPostId().equals(postInfo.getPostId())) {
                throw new DuplicateUserException("이미 존재하는 계정입니다.");
            }
        }

        Like like = new Like(postInfo);
        userInfo.getLike().add(like);

        userRepository.save(userInfo);
    }





    public void createUser(UserInfoDto userInfoDto) {

        UserInfo check = userRepository.findByUserId(userInfoDto.getUserId());

        if (check != null) {
            throw new DuplicateUserException("이미 존재하는 계정입니다.");
        }

        UserInfo userInfo = UserInfo.builder()
                .userId(userInfoDto.getUserId())
                .nickname(userInfoDto.getNickname())
                .address(userInfoDto.getAddress())
                .birth(userInfoDto.getBirth())
                .gender(userInfoDto.getGender())
                .build();

        userRepository.save(userInfo);
    }
    public void putUser(UserInfoDto userInfoDto) {

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId());

        checkExistUser(userInfo);

        userInfo.setNickname(userInfoDto.getNickname());
        userInfo.setAddress(userInfoDto.getAddress());
        userInfo.setBirth(userInfoDto.getBirth());
        userInfo.setGender(userInfoDto.getGender());
        userRepository.save(userInfo);
    }
    public void deleteUser(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        userRepository.deleteUser(userId);
    }

    public void cancelLike(LikeDto likeDto) {

//        UserInfo follower = userRepository.findByUserId(likeDto.getUserId());
//        UserInfo following = userRepository.findByUserId(likeDto.getPostId());

        userRepository.cancelLike(likeDto.getUserId(), likeDto.getPostId());

    }

    public void cancelFollow(FollowDto followDto) {

        UserInfo follower = userRepository.findByUserId(followDto.getFollower());
        UserInfo following = userRepository.findByUserId(followDto.getFollowing());

        checkExistFollowerAndFollowing(follower, following);
//        if (follower == null || following == null) {
//            throw new DataNotFoundException("존재하지 않는 계정입니다.");
//        }

        userRepository.cancelFollow(followDto.getFollower(), followDto.getFollowing());
    }

    public Long countFollower(String userId) {

        Long followerCount = userRepository.countFollower(userId);

        return followerCount;
    }
    public Long countFollowing(String userId) {

        Long followingCount = userRepository.countFollowing(userId);

        return followingCount;
    }

    public List<String> getFollowerList(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        return userRepository.getFollowerList(userId);
    }
    public List<String> getFollowingList(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        return userRepository.getFollowingList(userId);
    }


    public void checkExistUser(UserInfo userInfo) {

        if (userInfo == null) {
            throw new DataNotFoundException("존재하지 않는 계정 입니다.");
        }
    }
    public void checkExistFollowerAndFollowing(UserInfo follower, UserInfo following) {

        if (follower == null || following == null) {
            throw new DataNotFoundException("존재하지 않는 계정입니다.");
        }
    }
}