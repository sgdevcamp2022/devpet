package com.devpet.feed.service;

import com.devpet.feed.model.dto.CommentDto;
import com.devpet.feed.model.dto.LikePostDto;
import com.devpet.feed.model.dto.PostInfoDto;
import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.model.entity.Tag;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Comment;
import com.devpet.feed.model.relationship.Post;
import com.devpet.feed.repository.Neo4jRepository;
import com.devpet.feed.repository.PostInfoRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostInfoService {

    private final Neo4jRepository neo4jRepository;
    private final PostInfoRepository postInfoRepository;
    private final UserInfoRepository userInfoRepository;

    private PostInfo postInfoDtoToPostInfo(PostInfoDto dto) {
        return new PostInfo(dto);
    }

    private PostInfoDto postInfoToPostInfoDto(PostInfo info) {
        return new PostInfoDto(info);
    }

    /**
     * 포스트 정보 저장
     * @param postInfoDto
     * @return
     * @throws Exception
     */
    @Transactional
    public PostInfoDto savePostInfo(PostInfoDto postInfoDto){
        UserInfo userInfo = userInfoRepository.findNodeById(postInfoDto.getUserId()).orElseThrow(RuntimeException::new);
        PostInfo postInfo = postInfoDtoToPostInfo(postInfoDto);


        Set<Tag> postTags = postInfo.getTags();

        Post post = new Post(postInfo);
        userInfo.getPosts().add(post);
        userInfoRepository.save(userInfo);
        return postInfoToPostInfoDto(postInfoRepository.save(postInfo));
    }
//
//    /**
//     * 포스트 좋아요 하기
//     * @param likePostDto
//     * @return
//     * @throws Exception
//     */
//    @Transactional
//    public PostInfoDto likePostInfo(LikePostDto likePostDto){
//        // 게시글을 사용자가 좋아요 했는지 확인
//        Optional<UserInfo> userInfoOptional = userInfoRepository.existsLike(likePostDto.getPostId(), likePostDto.getUserId());
//        // 게시글 정보 가져오기
//        PostInfo postInfo = postInfoRepository.findNodeById(likePostDto.getPostId()).orElseThrow(RuntimeException::new);
//        // 이미 좋아요 했다면 해당 게시글Dto 반환
//        if(userInfoOptional.isPresent())
//            return postInfoToPostInfoDto(postInfo);
//        // 사용자 정보 가져오기
//        UserInfo userInfo = userInfoRepository.findNodeById(likePostDto.getUserId()).orElseThrow(RuntimeException::new);
//        // 좋아요 정보 생성
//        Like like = new Like(userInfo);
//        // 포스트에 좋아요 객체 연결
//        postInfo.getLikes().add(like);
//        return postInfoToPostInfoDto(postInfoRepository.save(postInfo));
//    }

    /**
     * 포스트 좋아요 하기
     * bulk insert version
     * @param likePostDto
     * @return
     * @throws Exception
     */
    @Transactional
    public ResponseEntity likePostInfo(List<LikePostDto> likePostDto){
//        var app = new Neo4jRepository(uri, username, password, Config.defaultConfig());
        neo4jRepository.saveLikeAll(likePostDto);
        return ResponseEntity.ok("SUCCESS");
    }

    /**
     * 포스트 좋아요 취소하기
     * @param likePostDto
     * @return
     */
    @Transactional
    public ResponseEntity dislikePostInfo(List<LikePostDto> likePostDto) {
        neo4jRepository.deleteLikeAll(likePostDto);
        return ResponseEntity.ok("SUCCESS");
    }
//    /**
//     * 게시글 좋아요 취소
//     * @param likePostDto 게시글 좋아요 정보
//     * @return 게시글 정보
//     */
//    @Transactional
//    public PostInfo dislikePostInfo(LikePostDto likePostDto){
//        // 해당 사용자가 포스트를 좋아요 했는지 검사
//        userInfoRepository.existsLike(likePostDto.getPostId(), likePostDto.getUserId()).orElseThrow(RuntimeException::new);
//        // 사용자가 db에 존재하는지 검사
//        userInfoRepository.findNodeById(likePostDto.getUserId()).orElseThrow(RuntimeException::new);
//        // 게시글이 db에 존재하는지 검사
//        postInfoRepository.findNodeById(likePostDto.getPostId()).orElseThrow(RuntimeException::new);
//        return postInfoRepository.dislikePost(likePostDto.getPostId(), likePostDto.getUserId());
//    }


    @Transactional
    public ResponseEntity<?> postComment(CommentDto commentDto)  {
        Optional<PostInfo> post = postInfoRepository.existsComment(commentDto.getPostId(), commentDto.getUserId());

        if(post.isPresent()){
            return ResponseEntity.badRequest().body("BadRequest");
        }
        UserInfo userInfo = userInfoRepository.findNodeById(commentDto.getUserId()).orElseThrow(RuntimeException::new);
        PostInfo postInfo = postInfoRepository.findNodeById(commentDto.getPostId()).orElseThrow(RuntimeException::new);
        Comment comment = new Comment(userInfo);
        comment.setCreatedAt(commentDto.getCreatedAt());
        postInfo.getComments().add(comment);
        postInfoRepository.save(postInfo);
        return ResponseEntity.ok(postInfo.getPostId());
    }

    @Transactional
    public ResponseEntity<?> getCommentPost(String userId) {
        UserInfo userInfo = userInfoRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(postInfoRepository.findCommentedPostById(userInfo.getUserId()));
    }
}
