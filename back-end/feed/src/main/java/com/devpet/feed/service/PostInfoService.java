package com.devpet.feed.service;

import com.devpet.feed.dto.PostInfoDto;
import com.devpet.feed.entity.PostInfo;
import com.devpet.feed.entity.UserInfo;
import com.devpet.feed.relationship.Post;
import com.devpet.feed.relationship.Tagged;
import com.devpet.feed.repository.PostInfoRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.neo4j.repository.support.SimpleNeo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostInfoService {
    private final PostInfoRepository postInfoRepository;
    private final UserInfoRepository userInfoRepository;

    private PostInfo postInfoDtoToPostInfo(PostInfoDto dto) {
        return new PostInfo(dto);
    }

    private PostInfoDto postInfoToPostInfoDto(PostInfo info) {
        return new PostInfoDto(info);
    }

    @Transactional
    public PostInfoDto savePostInfo(PostInfoDto postInfoDto) throws Exception{
        UserInfo userInfo = userInfoRepository.findById(postInfoDto.getUserId()).orElseThrow(()-> new Exception("존재하지 않는 계정입니다"));
        PostInfo postInfo = postInfoDtoToPostInfo(postInfoDto);


        Post post = new Post(postInfo);
        userInfo.getPosts().add(post);
        userInfoRepository.save(userInfo);
        return postInfoToPostInfoDto(postInfoRepository.save(postInfo));
    }
}
