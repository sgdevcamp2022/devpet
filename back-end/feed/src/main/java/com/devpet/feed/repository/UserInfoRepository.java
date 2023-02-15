package com.devpet.feed.repository;

import com.devpet.feed.model.entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserInfoRepository extends Neo4jRepository<UserInfo, String> {

    @Query("MATCH (m:UserInfo {id: $followedUser}) " +
            "MATCH (n:UserInfo {id: $followUser}) "+
            "MATCH (m)<-[F:FOLLOW]-(n)"+
            "DELETE F;" )
    UserInfo deleteFollowById(String followedUser, String followUser);

    @Query("MATCH (m:UserInfo {userId: $userId}) " + "RETURN m" )
    Optional<UserInfo> findNodeById (String userId);

    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId : $userId}) " +
            "MATCH (n)-[l:LIKE]->(m) "+
            "return n"
    )
    Optional<UserInfo> existsLike(String postId, String userId);

    @Query("match(u:UserInfo{userId : $userId})-[r:RECOMMENDED]->(p:PostInfo) " +
            "with u, r, p " +
            "ORDER BY r.score DESC " +
            "LIMIT 4 " +
            "match (u)-[r]->(p) " +
            "match (p)-[:TAGD]->(t:Tag)<-[:TAGD]-(n:PostInfo) " +
            "return n.postId")
    List<String> getPostList(@Param("userId") String userId);

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
    Set<String> getFollowerList(@Param("userId") String userId);

    @Query("match(f1:UserInfo{userId : $userId})-[:FOLLOW]->(f2:UserInfo)" + "return f2.userId")
    Set<String> getFollowingList(@Param("userId") String userId);

    // follow 관계가 있는지 체크
    @Query("match(u1:UserInfo{userId: $follower}) " +
            "match(u2:UserInfo{userId: $following}) " +
            "WHERE EXISTS((u1)-[:FOLLOW]->(u2)) " + "RETURN u1")
    UserInfo checkFollow(@Param("follower") String follower, @Param("following") String following);


    // 내가 팔로우 한 유저들이 작성한 게시글들 가져오기(시간순으로 정렬 과 개수 조정 필요)
    @Query("Match(u:UserInfo{userId: $userId})-[:FOLLOW]->(:UserInfo)-[:POST]->(p:PostInfo)" + "return p.postId")
    Set<String> getFollowPostList(@Param("userId") String userId);

    // 내가 좋아요를 누른 게시글의 tag 에 관련된 다른 게시글들 불러오기(시간순으로 정렬 과 개수 조정 필요)
    @Query("match(u:UserInfo{userId: $userId})-[:LIKE]->(:PostInfo)-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo)" + "return p.postId")
    Set<String> getLikePostList(@Param("userId") String userId);

    // 내가 댓글을 쓴 게시글의 tag에 관련된 다른 게시글들 불러오기(시간순으로 정렬 과 개수 조정 필요)
    @Query("match(u:UserInfo{userId: $userId})-[:COMMENT]->(:PostInfo)-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo)" + "return p.postId")
    Set<String> getCommentPostList(@Param("userId") String userId);

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

    Set<String> getFollowRecommendPostList(@Param("userId") String userId);

    // 내가 키우는 펫과 관련된 태그의 게시물
    @Query("match(u:UserInfo{userId: $userId})-[:PET]->()-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo) " +
            "return p.postId")
    Set<String> getPetPostList(@Param("userId") String userId);

    // List<String> getFollowRecommendPostList(@Param("userId") String userId);

    @Query("Match (u:UserInfo{userId: $userId1})-[:FOLLOW]->()-[f:FOLLOW]-()-[:POST]->(p:PostInfo)" +
            "Match (n:PostInfo)<-[:RECOMMENDED]-(u)" +
            "with n" +
            "limit 4" +
            "MATCH (p)-[:TAGD]->(:Tag)<-[:TAGD]-(n:PostInfo)" +
            "return DISTINCT p.postId")
    List<String> getFollowingRecommendPostList(String userId);

    @Query("match(u1:UserInfo{userId: $userId})-[:FOLLOW]->()-[:POST]->(p:PostInfo)" +
            "WITH datetime() AS now, datetime(p.createdAt) AS date , p" +
            "where duration.inSeconds(date, now).hours < 10" +
            "return p.postId")
    List<String> getFollowingNewPostList(String userId);

}
