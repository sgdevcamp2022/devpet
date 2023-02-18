package com.smilegate.devpet.appserver.api.relation;

import com.smilegate.devpet.appserver.model.GroupInfo;
import com.smilegate.devpet.appserver.model.JoinGroupRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId = "group-info", name="relation")
public interface GroupInfoApi {
    @PostMapping("")
    GroupInfo createGroup(GroupInfo userInfo);

    @GetMapping(path="/join")
    GroupInfo joinGroup(JoinGroupRequest userInfo);

    @PostMapping(path="/feed")
    GroupInfo leaveGroup(JoinGroupRequest joinGroupDto);
}
