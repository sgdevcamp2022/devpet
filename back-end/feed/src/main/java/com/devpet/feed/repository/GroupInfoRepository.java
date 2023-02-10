package com.devpet.feed.repository;

import com.devpet.feed.entity.GroupInfo;
import com.devpet.feed.entity.PostInfo;
import com.devpet.feed.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupInfoRepository extends Neo4jRepository<GroupInfo, String> {
    @Query("MATCH (m:GroupInfo {groupName: $groupName}) " +
            "MATCH (n:UserInfo{userId: $memberName})"+
            "MATCH (m)<-[F:Joined ]-(n)"+
            "DELETE F;" )
    GroupInfo leaveGroup(String groupName, String memberName);

    @Query("MATCH (m:GroupInfo {groupName : $groupName}) "+
            "RETURN m"
    )
    GroupInfo findNodeById(String groupName);


}
