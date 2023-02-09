package com.devpet.feed.controller;

import com.devpet.feed.dto.FollowMemberDto;
import com.devpet.feed.dto.UserInfoDto;
import com.devpet.feed.entity.UserInfo;
import com.devpet.feed.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")

@Slf4j
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    /**
     * 사용자 Node 생성
     * @param userInfo
     */
    @PostMapping("")
    public void saveUserInfo(@RequestBody UserInfoDto userInfo)  {
      userInfoService.saveUserInfo(userInfo);
    }

    /**
     * 사용자 Follow
     * @param followMemberDto
     * @return
     * @throws Exception
     */
    @PostMapping("/follow")
    public UserInfoDto followUser(@RequestBody FollowMemberDto followMemberDto)throws Exception{
        return userInfoService.followUser(followMemberDto.getUsername(), followMemberDto.getFollowUser());
    }

    /**
     * 사용자 정보 변경
     * @param userInfoDto
     * @return
     * @throws Exception
     */
    @PatchMapping("")
    public UserInfoDto patchUserInfo(@RequestBody UserInfoDto userInfoDto) throws Exception {
        userInfoDto = userInfoService.patchUserInfo(userInfoDto);
        return userInfoDto;
    }

    @PatchMapping("/follow")
    public UserInfo patchUserFollower(@RequestBody FollowMemberDto followMemberDto) throws Exception {
        UserInfo userInfoDto = userInfoService.patchFollower(followMemberDto.getUsername(), followMemberDto.getFollowUser());
        return userInfoDto;
    }
}
