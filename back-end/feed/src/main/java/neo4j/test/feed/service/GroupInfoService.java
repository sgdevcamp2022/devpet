package neo4j.test.feed.service;


import lombok.RequiredArgsConstructor;
import neo4j.test.feed.model.dto.GroupInfoDto;
import neo4j.test.feed.model.dto.JoinGroupDto;
import neo4j.test.feed.model.entity.GroupInfo;
import neo4j.test.feed.model.entity.UserInfo;
import neo4j.test.feed.model.relationship.Join;
import neo4j.test.feed.repository.GroupInfoRepository;
import neo4j.test.feed.repository.UserInfoRepository;
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
        UserInfo userInfo = userInfoRepository.findByUserId(joinGroupDto.getUserId());
        GroupInfo groupInfo = groupInfoRepository.findNodeById(joinGroupDto.getGroupName());

        Join member = new Join(userInfo);
        groupInfo.getMemberSet().add(member);
        return groupInfoToGroupInfoDto(groupInfoRepository.save(groupInfo));
    }

    @Transactional
    public GroupInfo leaveGroup(JoinGroupDto joinGroupDto) throws Exception{
        if (userInfoRepository.existsById(joinGroupDto.getUserId()) &&
                groupInfoRepository.existsById(joinGroupDto.getGroupName())){
            return groupInfoRepository.leaveGroup(joinGroupDto.getGroupName(), joinGroupDto.getUserId());
        }
        else {
            throw new Exception("존재하지 않는 데이터");
        }
    }
}
