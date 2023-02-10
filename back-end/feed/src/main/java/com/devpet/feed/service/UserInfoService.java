package com.devpet.feed.service;

import com.devpet.feed.dto.UserInfoDto;
import com.devpet.feed.entity.UserInfo;
import com.devpet.feed.relationship.Follow;

import com.devpet.feed.repository.UserInfoRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userRepository;

    private UserInfo userDtoToUserInfo(UserInfoDto dto) {
        return new UserInfo(dto);
    }

    private UserInfoDto userInfoToUserInfoDto(UserInfo info) {
        return new UserInfoDto(info);
    }

    @Transactional
    public UserInfoDto saveUserInfo(UserInfoDto userInfo) {
        UserInfo save = new UserInfo(userInfo);

        Optional<UserInfo> user = userRepository.findById(save.getUserId());
        if (user.isEmpty()) {
            save = userRepository.save(userDtoToUserInfo(userInfo));
        } else {
            throw new IllegalArgumentException("중복된 계정입니다");
        }
        return userInfoToUserInfoDto(save);
    }

    @Transactional
    public UserInfoDto followUser(String followedUser, String followUser) throws Exception {
        UserInfo user = userRepository.findById(followedUser).orElseThrow(() -> new Exception("존재하지 않는 계정입니다"));
        UserInfo follower = userRepository.findById(followUser).orElseThrow(() -> new Exception("존재하지 않는 계정입니다"));

        Set<Follow> userFollower = user.getFollowers();
        for (Follow entity : userFollower) {
            if (entity.getUserInfo().getUserId().equals(follower.getUserId())) {
                throw new DuplicateRequestException("이미 존재하는 계정입니다.");
            }
        }
        Follow followerNode = new Follow(follower);
        user.getFollowers().add(followerNode);


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

}
