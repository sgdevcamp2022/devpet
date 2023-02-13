package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Comment;
import com.smilegate.devpet.appserver.model.CommentRequest;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    public Comment postComment(long feedId, CommentRequest commentRequest, UserInfo userInfo)
    {
        if (commentRequest.getProfileId()==null)
        {
            commentRequest.setProfileId(profileService.getProfile(userInfo).getProfileId());
        }
        Comment comment = new Comment(commentRequest,feedId,sequenceGeneratorService.longSequenceGenerate(Comment.SEQUENCE_NAME));
        commentRepository.save(comment);
        return comment;
    }

    public Comment putComment(CommentRequest commentRequest)
    {
        Comment comment = commentRepository.findById(commentRequest.getCommentId()).orElseThrow(RuntimeException::new);
        comment.setComment(String.valueOf(commentRequest));
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getPostComment(Long postId,int start,int count)
    {
        Sort sort = Sort.by("createdAt");
        Page<Comment> pageResult = commentRepository.findByPostId(postId, PageRequest.of(start,count,sort));
        return pageResult.get().collect(Collectors.toList());
//        return commentRepository.findByPostId(postId);
    }

}
