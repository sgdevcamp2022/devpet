package neo4j.test.feed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo4j.test.feed.model.dto.FollowDto;
import neo4j.test.feed.model.dto.LikeDto;
import neo4j.test.feed.model.dto.UserInfoDto;
import neo4j.test.feed.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserInfoController {

//    private final UserInfoService userInfoService;
//
//    public UserInfoController(UserInfoService userInfoService) {
//        this.userInfoService = userInfoService;
//    }
//
//
//    /**
//     * 사용자 Node 생성
//     * @param userInfo
//     */
//    @PostMapping("")
//    public void saveUserInfo(@RequestBody UserInfoDto userInfo)  {
//      userInfoService.saveUserInfo(userInfo);
//    }
//
//    /**
//     * 사용자 Follow
//     * @param followMemberDto
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/follow")
//    public UserInfoDto followUser(@RequestBody FollowMemberDto followMemberDto)throws Exception{
//        return userInfoService.followUser(followMemberDto.getUsername(), followMemberDto.getFollowUser());
//    }
//
//    /**
//     * 사용자 정보 변경
//     * @param userInfoDto
//     * @return
//     * @throws Exception
//     */
//    @PatchMapping("")
//    public UserInfoDto patchUserInfo(@RequestBody UserInfoDto userInfoDto) throws Exception {
//        userInfoDto = userInfoService.patchUserInfo(userInfoDto);
//        return userInfoDto;
//    }
//
//    @PatchMapping("/follow")
//    public UserInfo patchUserFollower(@RequestBody FollowMemberDto followMemberDto) throws Exception {
//        UserInfo userInfoDto = userInfoService.patchFollower(followMemberDto.getUsername(), followMemberDto.getFollowUser());
//        return userInfoDto;
//    }

    private final UserInfoService userService;

    @PostMapping
    public void createUser(@RequestBody UserInfoDto userInfoDto) {

        userService.createUser(userInfoDto);
    }
    @PutMapping
    public ResponseEntity<String> putUser(@RequestBody UserInfoDto userInfoDto) {

        userService.putUser(userInfoDto);

        return ResponseEntity.ok("수정 성공");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/follow")
    public void followUser(@RequestBody FollowDto followDto) {

        userService.follow(followDto);
    }
    @PostMapping("/like")
    public void likePost(@RequestBody LikeDto likeDto) {

        userService.like(likeDto);
    }

    @PostMapping("/follow/cancel")
    public void cancelFollow(@RequestBody FollowDto followDto) {

        userService.cancelFollow(followDto);
    }

    @PostMapping("/like/cancel")
    public void cancelLike(@RequestBody LikeDto likeDto) {

        userService.cancelLike(likeDto);
    }


    @GetMapping("/count/follower/{userId}")
    public ResponseEntity<Long> countFollower(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.countFollower(userId));
    }
    @GetMapping("/count/following/{userId}")
    public ResponseEntity<Long> countFollowing(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.countFollowing(userId));
    }

    @GetMapping("/list/follower/{userId}")
    public ResponseEntity<List<String>> getFollowerList(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.getFollowerList(userId));
    }
    @GetMapping("/list/following/{userId}")
    public ResponseEntity<List<String>> getFollowingList(@PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.getFollowingList(userId));
    }

}
