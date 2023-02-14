package com.devpet.feed.repository;

import com.devpet.feed.model.entity.PostInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostInfoRepository extends Neo4jRepository<PostInfo, String> {
    @Query("MATCH (m:PostInfo {postId : $postId}) " +
            "RETURN m"
    )
    Optional<PostInfo> findNodeById(String postId);
    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId: $userId}) " +
            "MATCH (m)<-[L:LIKE]-(n)" +
            "DELETE L;")
    PostInfo dislikePost(String postId, String userId);

    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId : $userId}) " +
            "MATCH (n)-[r:RECOMMENDED]->(m) " +
            "return m;"
    )
    PostInfo existsRecommended(String postId, String userId);


}
