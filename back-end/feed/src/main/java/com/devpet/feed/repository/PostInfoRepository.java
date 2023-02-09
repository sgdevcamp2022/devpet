package com.devpet.feed.repository;

import com.devpet.feed.entity.PostInfo;
import com.devpet.feed.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInfoRepository extends Neo4jRepository<PostInfo, String> {
    @Query("MATCH (m:PostInfo {postId: $followedUser}) " +
            "MATCH (n:Tag {id: $followUser})"+
            "MATCH (m)<-[F:Follow]-(n)"+
            "DELETE F;" )
    UserInfo savePostAndTags(String postId, String Tag);


}
