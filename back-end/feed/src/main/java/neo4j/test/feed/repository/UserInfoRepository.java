package neo4j.test.feed.repository;

import neo4j.test.feed.model.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends Neo4jRepository<UserInfo, String> {

    @Query("MATCH (m:UserInfo {id: $followedUser}) " +
            "MATCH (n:UserInfo {id: $followUser}) "+
            "MATCH (m)<-[F:Follow]-(n)"+
            "DELETE F;" )
    UserInfo deleteFollowById(String followedUser, String followUser);

    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId : $userId}) " +
            "MATCH (n)-[l:likes]->(m) "+
            "return n"
    )
    UserInfo existsLike(String postId, String userId);

//    @Query("MATCH (m:UserInfo{userId: $userId}) " + " RETURN m;" )
//    UserInfo findNodeById (@Param("userId")String userId);

//    @Query("match(u:UserInfo{userId : $userId})" + "return u")
//    UserInfo findByUserId(@Param("userId") String userId);

    @Query("match(u:UserInfo{userId : $userId})" + "return u")
    UserInfo findByUserId(@org.springframework.data.repository.query.Param("userId") String userId);

    ///////////

    @Query("match(u:UserInfo{userId : $userId})" + "DETACH DELETE u")
    void deleteUser(@org.springframework.data.repository.query.Param("userId") String userId);

    @Query("match(u:UserInfo{userId : $userId})-[r:LIKE]->(p:PostInfo{postId : $postId}) " + "delete r")
    void cancelLike(@org.springframework.data.repository.query.Param("userId") String userId, @org.springframework.data.repository.query.Param("postId") String postId);

    @Query("match(f1:UserInfo {userId : $follower})-[r:FOLLOW]->(f2:UserInfo{userId: $following}) " + "delete r")
    void cancelFollow(@org.springframework.data.repository.query.Param("follower") String follower, @org.springframework.data.repository.query.Param("following") String following);

    @Query("match(f:UserInfo{userId : $userId})<-[:FOLLOW]-()" + "RETURN COUNT(f)")
    Long countFollower(@org.springframework.data.repository.query.Param("userId") String userId);

    @Query("match(f:UserInfo{userId : $userId})-[:FOLLOW]->()" + "RETURN COUNT(f)")
    Long countFollowing(@org.springframework.data.repository.query.Param("userId") String userId);

    @Query("match(f1:UserInfo{userId : $userId})<-[:FOLLOW]-(f2:UserInfo)" + "return f2.userId")
    List<String> getFollowerList(@org.springframework.data.repository.query.Param("userId") String userId);

    @Query("match(f1:UserInfo{userId : $userId})-[:FOLLOW]->(f2:UserInfo)" + "return f2.userId")
    List<String> getFollowingList(@org.springframework.data.repository.query.Param("userId") String userId);

    @Query("match(u:UserInfo{userId : $userId})-[r:has_Recommended]->(p:PostInfo) " +
            "with u, r, p " +
            "ORDER BY r.score DESC " +
            "LIMIT 4 " +
            "match (u)-[r]->(p) " +
            "match (p)-[:tagged]->(t:Tag)<-[:tagged]-(n:PostInfo) " +
            "return n.postId")
    List<String> getPostList(@org.springframework.data.repository.query.Param("userId") String userId);

    @Query("match(u1:UserInfo{userId: $follower}) " +
            "match(u2:UserInfo{userId: $following}) " +
            "WHERE EXISTS((u1)-[:FOLLOW]->(u2)) " + "RETURN u1")
    UserInfo checkFollow(@org.springframework.data.repository.query.Param("follower") String follower, @org.springframework.data.repository.query.Param("following") String following);


    // 내가 팔로우 한 유저들이 작성한 게시글들 가져오기
    @Query("Match(u:UserInfo{userId: $userId})-[:FOLLOW]->(:UserInfo)-[:POST]->(p:PostInfo)" + "return p.postId")
    List<String> getFollowPostList(@Param("userId") String userId);

    // 내가 좋아요를 누른 게시글의 tag 에 관련된 다른 게시글들 불러오기
    @Query("match(u:UserInfo{userId: $userId})-[:LIKE]->(:PostInfo)-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo)" + "return p.postId")
    List<String> getLikePostList(@Param("userId") String userId);

    // 내가 댓글을 쓴 게시글의 tag에 관련된 다른 게시글들 불러오기
    @Query("match(u:UserInfo{userId: $userId})-[:COMMENT]->(:PostInfo)-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo)" + "return p.postId")
    List<String> getCommentPostList(@Param("userId") String userId);

    /*
    * 내가 팔로우 한 유저들의 recommend 관계가 있는 게시글의 tag에 관련된 게시글들 불러오기
    * (시간순으로 정렬 과 개수 조정 필요)
    * */
    @Query("Match (u:UserInfo{userId: $userId})-[:FOLLOW]->()-[r:RECOMMENDED]->(p:PostInfo) " +
            "with r, p " +
            "ORDER BY r.score DESC " +
            "LIMIT 4 " +
            "MATCH (p)-[:TAGD]->(:Tag)<-[:TAGD]-(n:PostInfo) " +
            "return n.postId")
    List<String> getFollowRecommendPostList(@Param("userId") String userId);
}
