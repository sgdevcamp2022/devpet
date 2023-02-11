package com.devpet.feed.repository;

import com.devpet.feed.data.node.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<UserInfo, String> {

      @Query("match(u:UserInfo{userId : $userId})" + "return u")
      UserInfo findByUserId(@Param("userId") String userId);

      @Query("match(u:UserInfo{userId : $userId})" + "DETACH DELETE u")
      void deleteUser(@Param("userId") String userId);

      @Query("match(u:UserInfo{userId : $userId})-[r:LIKE]->(p:PostInfo{postId : $postId}) " + "delete r")
      void cancelLike(@Param("userId") String userId, @Param("postId") String postId);

      @Query("match(f1:UserInfo {userId : $follower})-[r:FOLLOW]->(f2:UserInfo{userId: $following}) " + "delete r")
      void cancelFollow(@Param("follower") String follower, @Param("following") String following);

      @Query("match(f:UserInfo{userId : $userId})<-[:FOLLOW]-()" + "RETURN COUNT(f)")
      Long countFollower(@Param("userId") String userId);

      @Query("match(f:UserInfo{userId : $userId})-[:FOLLOW]->()" + "RETURN COUNT(f)")
      Long countFollowing(@Param("userId") String userId);

      @Query("match(f1:UserInfo{userId : $userId})<-[:FOLLOW]-(f2:UserInfo)" + "return f2.userId")
      List<String> getFollowerList(@Param("userId") String userId);

      @Query("match(f1:UserInfo{userId : $userId})-[:FOLLOW]->(f2:UserInfo)" + "return f2.userId")
      List<String> getFollowingList(@Param("userId") String userId);


      @Query("match(u:UserInfo{userId : $userId})-[r:RECOMMENDED]->(p:PostInfo) " +
             "with u, r, p " +
             "ORDER BY r.score DESC " +
             "LIMIT 4 " +
             "match (u)-[r]->(p) " +
              "match (p)-[:TAGD]->(t:Tag)<-[:TAGD]-(n:PostInfo) " +
             "return n.postId")
      List<String> getPostList(@Param("userId") String userId);


}
