package com.devpet.feed.service;

import com.devpet.feed.common.Hash;
import com.devpet.feed.dto.ScoreDto;
import com.devpet.feed.entity.PostInfo;
import com.devpet.feed.entity.UserInfo;
import com.devpet.feed.relationship.Post;
import com.devpet.feed.relationship.Recommend;
import com.devpet.feed.repository.PostInfoRepository;
import com.devpet.feed.repository.RedisRepository;
import com.devpet.feed.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
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
                    PostInfo postInfo = postInfoRepository.findNodeById(scoreDto.getPostId());
                    Recommend recommend = new Recommend(postInfo, scoreDto);
                    recommends.add(recommend);
                }
            }

            UserInfo userInfo = userInfoRepository.findNodeById(userId);
            userInfo.getRecommends().addAll(recommends);
            userInfoRepository.save(userInfo);
        } else {
            average = numerator / denominator;
            average = Double.parseDouble(String.format("%.1f", average));

        }

        redisRepository.setCachedScore(userHash, average);
    }
}
