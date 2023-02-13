package com.devpet.feed.service;

import com.devpet.feed.common.Hash;
import com.devpet.feed.model.dto.ScoreDto;
import com.devpet.feed.model.entity.PostInfo;
import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Recommend;
import com.devpet.feed.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserInfoRepository userInfoRepository;
    private final PostInfoRepository postInfoRepository;
    private final RedisRepository redisRepository;

    @Transactional
    public void feedScore(List<ScoreDto> scoreDtoList) {

        String userId = scoreDtoList.get(0).getUserId();
        String userHash = Hash.getHash(userId);
        Optional<Double> score = Optional.ofNullable(redisRepository.getCachedScore(userHash));
        double denominator = scoreDtoList.size();
        double numerator = scoreDtoList.stream().mapToDouble(ScoreDto::getScore).sum();
        double average;

        if (score.isPresent()) {
            average = score.get();
            average = average * 0.4 + (numerator / denominator) * 0.6;
            average = Double.parseDouble(String.format("%.1f", average));
            double finalAverage = average;
            List<ScoreDto> scoreList = scoreDtoList.stream().filter(s -> s.getScore() > finalAverage).collect(Collectors.toList());
            Set<Recommend> recommends = new HashSet<>();

            for (ScoreDto scoreDto : scoreList) {
                Optional<PostInfo> relationCheck = Optional.ofNullable(postInfoRepository.existsRecommended(scoreDto.getPostId(), userId));
                if(relationCheck.isEmpty()){
                    PostInfo postInfo = postInfoRepository.findNodeById(scoreDto.getPostId()).orElseThrow(RuntimeException::new);
                    Recommend recommend = new Recommend(postInfo, scoreDto);
                    recommends.add(recommend);
                }
            }

            UserInfo userInfo = userInfoRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
            userInfo.getRecommends().addAll(recommends);
            userInfoRepository.save(userInfo);
        } else {
            average = numerator / denominator;
            average = Double.parseDouble(String.format("%.1f", average));

        }

        redisRepository.setCachedScore(userHash, average);
    }

    public List<String> getPostList(String userId) {
        return userInfoRepository.getPostList(userId);
    }



    public void recommended(ScoreDto scoreDto) {
        // 추천하려는 대상이 db에 존재하는지 확인
        UserInfo userInfo = userInfoRepository.findNodeById(scoreDto.getUserId()).orElseThrow(RuntimeException::new);

        // 추천하려는 포스트가 db에 존재하는지 확인
        PostInfo postInfo = postInfoRepository.findNodeById(scoreDto.getPostId()).orElseThrow(RuntimeException::new);

        Set<Recommend> user = userInfo.getRecommends();
        for (Recommend entity : user) {
            if (entity.getPostInfo().getPostId().equals(postInfo.getPostId())) {
                throw new RuntimeException("이미 존재하는 계정입니다.");
            }
        }

        Recommend recommended = new Recommend(postInfo,scoreDto);
        userInfo.getRecommends().add(recommended);

        userInfoRepository.save(userInfo);
    }

}
