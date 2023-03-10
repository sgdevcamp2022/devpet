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
    List<UserInfo> findAllByUserIdIn(List<String> userIds);
    @Query("MATCH (m:UserInfo {id: $followedUser}) " +
            "MATCH (n:UserInfo {id: $followUser}) "+
            "MATCH (m)<-[F:FOLLOW]-(n)"+
            "DELETE F;" )
    UserInfo deleteFollowById(String followedUser, String followUser);
    @Query("MATCH (m:UserInfo {userId: $userId}) " + "RETURN m" )
    Optional<UserInfo> findNodeById (@Param("userId") String userId);
    @Query("MATCH (m:UserInfo {userId: $userId}) " + "RETURN m" )
    UserInfo findNodeById2(String userId);
    @Query("MATCH (m:PostInfo {postId: $postId}) " +
            "MATCH (n:UserInfo {userId : $userId}) " +
            "MATCH (n)-[l:LIKE]->(m) "+
            "return n"
    )
    Optional<UserInfo> existsLike(String postId, String userId);

    @Query("match(u:UserInfo{userId : $userId})-[r:RECOMMENDED]->(p:PostInfo) " +
            "with u, r, p " +
            "LIMIT 6 " +
            "match (u)-[r]->(p) " +
            "match (p)-[:TAGD]->(t:Tag)<-[:TAGD]-(n:PostInfo) " +
            "with n " +
            "order by n.createdAt DESC "+
            "return DISTINCT n.postId LIMIT 20"
            )
    Set<String> getPostList(@Param("userId") String userId);


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

    @Query("match(f1:UserInfo{userId : $userId})<-[:FOLLOW]-(f2:UserInfo) " + "return DISTINCT f2.userId")
    Set<String> getFollowerList(@Param("userId") String userId);

    @Query("match(f1:UserInfo{userId : $userId})-[:FOLLOW]->(f2:UserInfo)" + "return DISTINCT f2.userId")
    Set<String> getFollowingList(@Param("userId") String userId);

    // follow ????????? ????????? ??????
    @Query("match(u1:UserInfo{userId: $follower}) " +
            "match(u2:UserInfo{userId: $following}) " +
            "WHERE EXISTS((u1)-[:FOLLOW]->(u2)) " + "RETURN u1")
    UserInfo checkFollow(@Param("follower") String follower, @Param("following") String following);

    @Query("match(u1:UserInfo{userId: $follower}) " +
            "match(u2:UserInfo{userId: $following}) " +
            "WHERE EXISTS((u1)-[:FOLLOW]->(u2)) " + "RETURN u1")
    UserInfo saveFollow(@Param("follower") String follower, @Param("following") String following);

    // ?????? ????????? ??? ???????????? ????????? ???????????? ????????????(??????????????? ?????? ??? ?????? ?????? ??????)
    @Query("Match(u:UserInfo{userId: $userId})-[:FOLLOW]->(:UserInfo)-[:POST]->(p:PostInfo)" +
            "with DISTINCT p, p.postId as postId " +
            "return postId " +
            "order by p.createdAt DESC")
    Set<String> getFollowPostList(@Param("userId") String userId);

    // ?????? ???????????? ?????? ???????????? tag ??? ????????? ?????? ???????????? ????????????(??????????????? ?????? ??? ?????? ?????? ??????)
    @Query("match(u:UserInfo{userId: $userId})-[:LIKE]->(:PostInfo)-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo) " +
            "with DISTINCT p, p.postId as postId " +
            "return postId " +
            "order by p.createdAt DESC")
    Set<String> getLikePostList(@Param("userId") String userId);

    // ?????? ????????? ??? ???????????? tag??? ????????? ?????? ???????????? ????????????(??????????????? ?????? ??? ?????? ?????? ??????)
    @Query("match(u:UserInfo{userId: $userId})-[:COMMENT]->(:PostInfo)-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo)" +
            "with DISTINCT p, p.postId as postId " +
            "return postId " +
            "order by p.createdAt DESC")
    Set<String> getCommentPostList(@Param("userId") String userId);

    /*
     * ?????? ????????? ??? ???????????? recommend ????????? ?????? ???????????? tag??? ????????? ???????????? ????????????
     * (??????????????? ?????? ??? ?????? ?????? ??????)
     * */
    @Query("Match(u:UserInfo{userId: $userId})-[:FOLLOW]->()-[r:RECOMMENDED]->(p:PostInfo) " +
            "with r, p " +
            "ORDER BY r.score DESC " +
            "LIMIT 6 " +
            "MATCH (p)-[:TAGD]->(:Tag)<-[:TAGD]-(n:PostInfo) " +
            "with DISTINCT n, n.postId as postId " +
            "return postId " +
            "order by n.createdAt DESC")

    Set<String> getFollowRecommendPostList(@Param("userId") String userId);

    // ?????? ????????? ?????? ????????? ????????? ?????????
    @Query("match(u:UserInfo{userId: $userId})-[:PET]->()-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo) " +
            "with DISTINCT p, p.postId as postId " +
            "return postId " +
            "order by p.createdAt DESC")
    Set<String> getPetPostList(@Param("userId") String userId);

    // List<String> getFollowRecommendPostList(@Param("userId") String userId);

    @Query("Match (u:UserInfo{userId: $userId1})-[:FOLLOW]->()-[f:FOLLOW]-()-[:POST]->(p:PostInfo) " +
            "Match (n:PostInfo)<-[:RECOMMENDED]-(u) " +
            "with n " +
            "limit 6 " +
            "MATCH (p)-[:TAGD]->(:Tag)<-[:TAGD]-(n:PostInfo) " +
            "return DISTINCT p.postId")
    List<String> getFollowingRecommendPostList(String userId);

    @Query("match(u1:UserInfo{userId: $userId})-[:FOLLOW]->()-[]->(p:PostInfo) " +

            "WITH datetime() AS now, datetime(p.createdAt) AS date , p " +
            "where duration.inSeconds(date, now).hours < 10 " +
            "return p.postId")
    List<String> getFollowingNewPostList(String userId);

    // ????????? ?????????, ??????, ????????? ?????? ????????? ????????? ????????? + ???????????? ????????? ????????? ?????????(????????? ??????)
    @Query("match (u1:UserInfo{userId: $userId})-[:FOLLOW]->()-[:POST]->(p1:PostInfo) " +
            "WITH p1, datetime() AS now, datetime(p1.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours < 24 " +
            "return p1.postId as postId " +
            "union " +
            "match (u:UserInfo{userId: $userId})-[:PET]->()-[:TAGD]->(:Tag)<-[:TAGD]-(p:PostInfo) " +
            "WITH p, datetime() AS now, datetime(p.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours < 24 " +
            "return p.postId as postId " +
            "union " +
            "match (u1:UserInfo{userId: $userId})-[:LIKE]->(p1:PostInfo)-[:TAGD]->(t1:Tag)<-[:TAGD]-(n1:PostInfo) " +
            "WITH n1, datetime() AS now, datetime(n1.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours  < 24 " +
            "return n1.postId as postId " +
            "union " +
            "match (u2:UserInfo{userId: $userId})-[:COMMENT]->(p2:PostInfo)-[:TAGD]->(t2:Tag)<-[:TAGD]-(n2:PostInfo) " +
            "WITH n2, datetime() AS now, datetime(n2.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours  < 24 " +
            "return n2.postId as postId ")
    Set<String> getPetLikeCommentPostList(@Param("userId") String userId);

    @Query("Match(u1:UserInfo{userId: $userId})-[:FOLLOW]->()-[r1:RECOMMENDED]->(p1:PostInfo) " +
            "with r1, p1 " +
            "ORDER BY r1.score DESC " +
            "LIMIT 6 " +
            "MATCH (p1)-[:TAGD]->(:Tag)<-[:TAGD]-(n1:PostInfo) " +
            "WITH n1, datetime() AS now, datetime(n1.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours < 24 " +
            "return n1.postId as postId " +
            "union " +
            "match(u2:UserInfo{userId : $userId})-[r2:RECOMMENDED]->(p2:PostInfo) " +
            "with r2, p2 " +
            "ORDER BY r2.score DESC " +
            "LIMIT 6 " +
            "match (p2)-[:TAGD]->(:Tag)<-[:TAGD]-(n2:PostInfo) " +
            "WITH n2, datetime() AS now, datetime(n2.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours < 24 " +
            "return n2.postId as postId " +
            "union " +
            "Match (u:UserInfo{userId: $userId})-[:FOLLOW]->()-[f:FOLLOW]-()-[:POST]->(p:PostInfo) " +
            "Match (n:PostInfo)<-[:RECOMMENDED]-(u) " +
            "with n " +
            "limit 6 " +
            "MATCH (p)-[:TAGD]->(:Tag)<-[:TAGD]-(n:PostInfo) " +
            "with p, datetime() AS now, datetime(p.createdAt) AS date " +
            "where duration.inSeconds(date, now).hours < 24 " +
            "return DISTINCT p.postId as postId ")
    Set<String> getRecommendedFollowPostList(@Param("userId") String userId);
}

