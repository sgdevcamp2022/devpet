package com.devpet.feed.dto;


import com.devpet.feed.entity.GroupInfo;
import com.devpet.feed.relationship.Join;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor

public class GroupInfoDto {
    private String groupName;
    private String groupLeader;
    private Set<Join> memberSet;

    public GroupInfoDto(GroupInfo groupInfo) {
        this.groupName = groupInfo.getGroupName();
        this.groupLeader = groupInfo.getGroupLeader();
        this.memberSet = groupInfo.getMemberSet();
    }
}
