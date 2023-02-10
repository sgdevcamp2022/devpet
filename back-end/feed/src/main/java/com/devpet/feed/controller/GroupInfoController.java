package com.devpet.feed.controller;

import com.devpet.feed.dto.GroupInfoDto;
import com.devpet.feed.dto.JoinGroupDto;
import com.devpet.feed.entity.GroupInfo;
import com.devpet.feed.service.GroupInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("groupinfo")
public class GroupInfoController {

    private final GroupInfoService groupInfoService;

    @PostMapping("")
    public GroupInfoDto createGroup(@RequestBody GroupInfoDto groupInfoDto) {
        return groupInfoService.createGroup(groupInfoDto);

    }

    @PostMapping("/join")
    public GroupInfoDto joinGroup(@RequestBody JoinGroupDto joinGroupDto) {
        return groupInfoService.joinGroup(joinGroupDto);
    }

    @PatchMapping("/join")
    public GroupInfo leaveGroup(@RequestBody JoinGroupDto joinGroupDto) throws Exception {
        return groupInfoService.leaveGroup(joinGroupDto);
    }
}
