package neo4j.test.feed.repository;

import neo4j.test.feed.model.entity.PostInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInfoRepository extends Neo4jRepository<PostInfo, String> {

    @Query("MATCH (m:PostInfo {postId : $postId}) " + "RETURN m")
    PostInfo findNodeById(String postId);

    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId: $userId}) " +
            "MATCH (m)<-[L:likes]-(n)" +
            "DELETE L;")
    PostInfo dislikePost(String postId, String userId);

    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId : $userId}) " +
            "MATCH (n)-[r:has_Recommended]->(m) " +
            "return m;")
    PostInfo existsRecommended(String postId, String userId);


}
