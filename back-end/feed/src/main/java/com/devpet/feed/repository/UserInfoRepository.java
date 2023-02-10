package com.devpet.feed.repository;

import com.devpet.feed.entity.UserInfo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends Neo4jRepository<UserInfo, String> {
    @Query("MATCH (m:UserInfo {id: $followedUser}) " +
            "MATCH (n:UserInfo {id: $followUser}) "+
            "MATCH (m)<-[F:Follow]-(n)"+
            "DELETE F;" )
    UserInfo deleteFollowById(String followedUser, String followUser);
    @Query("MATCH (m:UserInfo {userId: $userId}) " +
            "RETURN m;" )
    UserInfo findNodeById (String userId);
    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId : $userId}) " +
            "MATCH (n)-[l:likes]->(m) "+
            "return n"
    )
    UserInfo existsLike(String postId, String userId);

    @Query("match(u:UserInfo{userId : $userId})-[r:has_Recommended]->(p:PostInfo) " +
            "with u, r, p " +
            "ORDER BY r.score DESC " +
            "LIMIT 4 " +
            "match (u)-[r]->(p) " +
            "match (p)-[:tagged]->(t:Tag)<-[:tagged]-(n:PostInfo) " +
            "return n.postId")
    List<String> getPostList(@Param("userId") String userId);
}
