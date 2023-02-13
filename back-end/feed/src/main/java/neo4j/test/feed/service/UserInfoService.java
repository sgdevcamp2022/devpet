package neo4j.test.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo4j.test.feed.exception.DataNotFoundException;
import neo4j.test.feed.exception.DuplicateUserException;
import neo4j.test.feed.model.dto.FollowDto;
import neo4j.test.feed.model.dto.LikeDto;
import neo4j.test.feed.model.dto.UserInfoDto;
import neo4j.test.feed.model.entity.PostInfo;
import neo4j.test.feed.model.entity.UserInfo;
import neo4j.test.feed.model.relationship.Follow;
import neo4j.test.feed.model.relationship.Like;
import neo4j.test.feed.repository.PostInfoRepository;
import neo4j.test.feed.repository.UserInfoRepository;
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

//    private UserInfo userDtoToUserInfo(UserInfoDto dto) {
//
//        return new UserInfo(dto);
//    }
//    private UserInfoDto userInfoToUserInfoDto(UserInfo info) {
//
//        return new UserInfoDto(info);
//    }

//    @Transactional
//    public UserInfoDto saveUserInfo(UserInfoDto userInfo) {
//        UserInfo save = new UserInfo(userInfo);
//
//        Optional<UserInfo> user = userRepository.findById(save.getUserId());
//        if (user.isEmpty()) {
//            save = userRepository.save(userDtoToUserInfo(userInfo));
//        } else {
//            throw new IllegalArgumentException("중복된 계정입니다");
//        }
//        return userInfoToUserInfoDto(save);
//    }
//
//    @Transactional
//    public UserInfoDto followUser(String followedUser, String followUser) throws Exception {
//        UserInfo user = userRepository.findById(followedUser).orElseThrow(() -> new Exception("존재하지 않는 계정입니다"));
//        UserInfo follower = userRepository.findById(followUser).orElseThrow(() -> new Exception("존재하지 않는 계정입니다"));
//
//        Set<Follow> userFollower = user.getFollower();
//        for (Follow entity : userFollower) {
//            if (entity.getUserInfo().getUserId().equals(follower.getUserId())) {
//                throw new DuplicateRequestException("이미 존재하는 계정입니다.");
//            }
//        }
//        Follow followerNode = new Follow(follower);
//        user.getFollower().add(followerNode);
//
//
//        return userInfoToUserInfoDto(userRepository.save(user));
//    }
//
//    @Transactional
//    public UserInfoDto patchUserInfo(UserInfoDto userInfoDto) throws Exception {
//        UserInfo userInfo = userDtoToUserInfo(userInfoDto);
//        if (userRepository.existsById(userInfo.getUserId())) {
//            return userInfoToUserInfoDto(userRepository.save(userInfo));
//        } else throw new Exception("계정이 존재하지 않습니다.");
//    }
//
//    public UserInfo patchFollower(String followedUser, String followUser) {
//
//        return userRepository.deleteFollowById(followedUser , followUser);
//    }

//////
    //// cud 기능

    @Transactional
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

    @Transactional
    public void putUser(UserInfoDto userInfoDto) {

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId());

        checkExistUser(userInfo);

        userInfo.setNickname(userInfoDto.getNickname());
        userInfo.setAddress(userInfoDto.getAddress());
        userInfo.setBirth(userInfoDto.getBirth());
        userInfo.setGender(userInfoDto.getGender());
        userRepository.save(userInfo);
    }

    @Transactional
    public void deleteUser(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        userRepository.deleteUser(userId);
    }

    //// cud 기능


    /// 팔로우, 팔로우 취소, 팔로워 팔로잉 수 또는 userId 리스트로 불러오기, 좋아요 , 좋아요 취소

    @Transactional
    public void follow(FollowDto followDto) {

        UserInfo following = userRepository.findByUserId(followDto.getFollowing());
        UserInfo follower = userRepository.findByUserId(followDto.getFollower());

        checkExistFollowerAndFollowing(following, follower);

        UserInfo check = userRepository.checkFollow(followDto.getFollower(), followDto.getFollowing());

        if (check != null) {
            throw new DuplicateUserException("이미 존재하는 관계입니다.");
        }

//        Set<Follow> userFollower = following.getFollower();
//        for (Follow entity : userFollower) {
//            if (entity.getUserInfo().getUserId().equals(follower.getUserId())) {
//                throw new DuplicateUserException("이미 존재하는 계정입니다.");
//            }
//        }

        Follow followerNode = new Follow(follower);
        following.getFollower().add(followerNode);

        userRepository.save(following);
    }

    @Transactional
    public void like(LikeDto likeDto) {

        UserInfo userInfo = userRepository.findByUserId(likeDto.getUserId());
        PostInfo postInfo = postRepository.findNodeById(likeDto.getPostId());

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

    @Transactional
    public void cancelLike(LikeDto likeDto) {

//        UserInfo follower = userRepository.findByUserId(likeDto.getUserId());
//        UserInfo following = userRepository.findByUserId(likeDto.getPostId());

        userRepository.cancelLike(likeDto.getUserId(), likeDto.getPostId());

    }

    @Transactional
    public void cancelFollow(FollowDto followDto) {

        UserInfo follower = userRepository.findByUserId(followDto.getFollower());
        UserInfo following = userRepository.findByUserId(followDto.getFollowing());

        checkExistFollowerAndFollowing(follower, following);

        userRepository.cancelFollow(followDto.getFollower(), followDto.getFollowing());
    }

    @Transactional
    public Long countFollower(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        return userRepository.countFollower(userId);
    }

    @Transactional
    public Long countFollowing(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        return userRepository.countFollowing(userId);
    }

    @Transactional
    public List<String> getFollowerList(String userId) {

        UserInfo userInfo = userRepository.findByUserId(userId);
        checkExistUser(userInfo);

        return userRepository.getFollowerList(userId);
    }

    @Transactional
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
