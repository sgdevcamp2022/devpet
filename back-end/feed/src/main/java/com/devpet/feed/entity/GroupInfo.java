package com.devpet.feed.entity;

import com.devpet.feed.dto.GroupInfoDto;
import com.devpet.feed.relationship.Join;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node
@Getter
@Setter
@RequiredArgsConstructor
public class GroupInfo {
    @Id
    private String groupName;

    @Property
    private String groupLeader;
    @Relationship(type = "Joined", direction = INCOMING)
    private Set<Join> memberSet ;

    public GroupInfo(GroupInfoDto groupInfoDto) {
        this.groupName = groupInfoDto.getGroupName();
        this.groupLeader = groupInfoDto.getGroupLeader();
        this.memberSet = groupInfoDto.getMemberSet();
    }


}
