package com.devpet.feed.model.dto;


import com.devpet.feed.model.entity.GroupInfo;
import com.devpet.feed.model.relationship.Join;
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
