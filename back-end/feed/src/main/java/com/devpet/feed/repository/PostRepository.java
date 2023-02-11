package com.devpet.feed.repository;

import com.devpet.feed.data.node.PostInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends Neo4jRepository<PostInfo, String> {

    @Query("match(p:PostInfo{postId : $postId})" + "return p")
    PostInfo findByPostId(@Param("postId") String postId);

}