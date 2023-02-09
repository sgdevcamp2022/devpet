package com.devpet.feed.repository;

import com.devpet.feed.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends Neo4jRepository<UserInfo, String> {
    @Query("MATCH (m:UserInfo {id: $followedUser}) " +
            "MATCH (n:UserInfo {id: $followUser})"+
            "MATCH (m)<-[F:Follow]-(n)"+
            "DELETE F;" )
    UserInfo deleteFollowById(String followedUser, String followUser);
}
