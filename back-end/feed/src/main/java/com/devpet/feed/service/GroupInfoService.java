package com.devpet.feed.service;

import com.devpet.feed.model.dto.GroupInfoDto;
import com.devpet.feed.model.dto.JoinGroupDto;
import com.devpet.feed.model.entity.GroupInfo;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Join;
import com.devpet.feed.repository.GroupInfoRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupInfoService {

    private final GroupInfoRepository groupInfoRepository;
    private final UserInfoRepository userInfoRepository;

    private GroupInfoDto groupInfoToGroupInfoDto(GroupInfo groupInfo) {
        return new GroupInfoDto(groupInfo);
    }

    private GroupInfo groupInfoDtoToGroupInfo(GroupInfoDto groupInfoDto) {
        return new GroupInfo(groupInfoDto);
    }

    @Transactional
    public GroupInfoDto createGroup(GroupInfoDto groupInfoDto) {
        UserInfo groupLeader = userInfoRepository.findById(groupInfoDto.getGroupLeader()).orElseThrow();

        Join join = new Join(groupLeader);
        Set<Join> member = new HashSet<>();
        member.add(join);
        GroupInfo groupInfo = groupInfoDtoToGroupInfo(groupInfoDto);
        groupInfo.setMemberSet(member);
        return groupInfoToGroupInfoDto(groupInfoRepository.save(groupInfo));
    }
    @Transactional
    public GroupInfoDto joinGroup(JoinGroupDto joinGroupDto) {
        UserInfo userInfo = userInfoRepository.findNodeById(joinGroupDto.getUserId()).orElseThrow(RuntimeException::new);
        GroupInfo groupInfo = groupInfoRepository.findNodeById(joinGroupDto.getGroupName());

        Join member = new Join(userInfo);
        groupInfo.getMemberSet().add(member);
        return groupInfoToGroupInfoDto(groupInfoRepository.save(groupInfo));
    }

    @Transactional
    public GroupInfo leaveGroup(JoinGroupDto joinGroupDto){
        if (!userInfoRepository.existsById(joinGroupDto.getUserId()) ||
                !groupInfoRepository.existsById(joinGroupDto.getGroupName()))
            throw new RuntimeException("not found data");
        return groupInfoRepository.leaveGroup(joinGroupDto.getGroupName(), joinGroupDto.getUserId());
    }
}
