package neo4j.test.feed.model.dto;


import lombok.*;
import neo4j.test.feed.model.entity.GroupInfo;
import neo4j.test.feed.model.relationship.Join;

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
