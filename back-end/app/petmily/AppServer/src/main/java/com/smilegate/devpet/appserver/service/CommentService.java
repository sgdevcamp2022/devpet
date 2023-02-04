package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Comment;
import com.smilegate.devpet.appserver.model.CommentRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProfileService profileService;
    private final SequenceGeneratorService sequenceGeneratorService;


    @Transactional
    public Comment postComment(long feedId, CommentRequest commentRequest, UserInfo userInfo)
    {
        if (commentRequest.getUserProfile()==null)
        {
            commentRequest.setUserProfile(profileService.getProfile(userInfo));
        }
        Comment comment = new Comment(commentRequest,feedId,sequenceGeneratorService.longSequenceGenerate(Comment.SEQUENCE_NAME));
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Comment putComment(long commentId, CommentRequest commentRequest)
    {
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        comment.setComment(String.valueOf(commentRequest));
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getPostComment(Long postId)
    {
        return commentRepository.findByPostIdOrderByCreatedAt(postId,PageRequest.of(1,3)).get().collect(Collectors.toList());
    }

}
