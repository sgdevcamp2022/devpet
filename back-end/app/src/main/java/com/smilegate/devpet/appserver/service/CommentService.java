package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.api.relation.PostInfoApi;
import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.repository.mongo.CommentRepository;
import com.smilegate.devpet.appserver.repository.mongo.FeedRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProfileService profileService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final NewPostRedisRepository newPostRedisRepository;
    private final FavoriteService favoriteService;
    private final PostInfoApi postInfoApi;
    private final FeedRepository feedRepository;


    /**
     * 게시글에 달린 코멘트를 저장합니다.
     * @param feedId 게시글 아이디
     * @param commentRequest 코멘트 정보
     * @param userInfo 사용자 정보
     * @return 저장된 코멘트 데이터
     */
    public Comment postComment(long feedId, CommentRequest commentRequest, UserInfo userInfo)
    {
        // profile 정보가 코멘트 정보에 있는지 확인합니다. 없다면 사용자 정보(userInfo)에서 가져와서 저장합니다.
        if (commentRequest.getProfileId()==null)
            commentRequest.setProfileId(profileService.getProfile(userInfo).getProfileId());

        // id가 설정된 코멘트 데이터를 생성합니다.
        Comment comment = new Comment(commentRequest,feedId,sequenceGeneratorService.longSequenceGenerate(Comment.SEQUENCE_NAME));

        // app db에 저장합니다.
        commentRepository.save(comment);

        // 코멘트를 작성한 사용자 피드 캐시에 저장합니다.
        newPostRedisRepository.save(userInfo.getUsername(), comment.getPostId());

        // 게시글 작성자에게 피드 캐시에 저장.
        feedRepository.findById(feedId).ifPresent((feed)->{
            // 작성자가 자기 글에 댓글을 단 경우 알람이 가지 않음...?
            Profile postUserProfile = profileService.getProfile(feed.getProfileId());
//            if (!Objects.equals(commentRequest.getProfileId(), profileService.getProfileByUserId(feed.getUserId()).getProfileId()))
                newPostRedisRepository.save(postUserProfile.getUsername(),feed.getFeedId());
        });
        // 상위 댓글 작성자가 있다면 상위 댓글 작성자의 피드 캐시에 저장.
        if (commentRequest.getParentCommentId()!=null)
            commentRepository.findById(commentRequest.getParentCommentId()).ifPresent((parentComment)->{
                // 댓글 작성자와 대댓글 작성자가 같은 경우 피드 캐시에 추가하지 않음.
                if (parentComment.getProfileId() != commentRequest.getProfileId())
                    newPostRedisRepository.save(
                            profileService.getProfile(parentComment.getProfileId()).getUsername(),comment.getPostId()
                    );
            });

        // 해당 게시글을 좋아요를 누르고 있는 사람들에게 피드 캐시에 댓글이 달렸다고 전송합니다.
        favoriteService.favoriteUserAddFeed(feedId);

        // 코멘트 정보를 관계정보 서버로 전송합니다.
        CommentRelationRequest commentRelationRequest = CommentRelationRequest.builder()
                .postId(Long.valueOf(comment.getPostId()).toString())
                .userId(userInfo.getUsername())
                .createdAt(comment.getCreatedAt().toEpochSecond(ZoneOffset.ofHours(9))).build();
        postInfoApi.postComment(commentRelationRequest);
        return comment;
    }

    /**
     * 코멘트를 수정합니다.
     * @param commentRequest 코멘트 수정 요청 정보
     * @return db의 수정된 코멘트 요청 정보
     */
    public Comment putComment(CommentRequest commentRequest)
    {
        Comment comment = commentRepository.findById(commentRequest.getCommentId()).orElseThrow(NullPointerException::new);
        comment.setComment(String.valueOf(commentRequest));
        commentRepository.save(comment);
        return comment;
    }

    /**
     * 코멘트 리스트를 조회합니다.
     * @param postId 조회할 게시글 아이디
     * @param start 조회 시작 페이지
     * @param count 조회할 갯수
     * @return 코멘트 리스트
     */
    public List<Comment> getPostComment(Long postId,int start,int count)
    {
        Sort sort = Sort.by("createdAt");
        Page<Comment> pageResult = commentRepository.findByPostId(postId, PageRequest.of(start,count,sort));
        return pageResult.get().collect(Collectors.toList());
//        return commentRepository.findByPostId(postId);
    }

}
