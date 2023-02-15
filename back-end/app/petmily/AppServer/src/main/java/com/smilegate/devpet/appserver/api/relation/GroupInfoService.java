package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.GroupInfo;
import com.smilegate.devpet.appserver.model.JoinGroupRequest;
import com.smilegate.devpet.appserver.model.ScoreRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "relation", value = "group_info")
public interface GroupInfoService {
    @PostMapping("")
    GroupInfo createGroup(GroupInfo userInfo);

    @GetMapping(path="/join")
    GroupInfo joinGroup(JoinGroupRequest userInfo);

    @PostMapping(path="/feed")
    GroupInfo leaveGroup(JoinGroupRequest joinGroupDto);
}
