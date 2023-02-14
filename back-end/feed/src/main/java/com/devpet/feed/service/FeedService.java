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
        // 피드를 계산할 사용자 아이디 & 해시값 가져오기
        String userId = scoreDtoList.get(0).getUserId();
        String userHash = Hash.getHash(userId);
        Optional<Double> scoreOptional = Optional.ofNullable(redisRepository.getCachedScore(userHash));
        // 점수 총 개수
        double denominator = scoreDtoList.size();
        // 점수 총 합
        double numerator = scoreDtoList.stream().mapToDouble(ScoreDto::getScore).sum();
        double average = 0d;
        double isCached = 1d;

        // 일전에 기록된 점수라면, 이전 점수 * 0.4 + 현재 점수(점수 총 합 / 갯수) * 0.6
        // 처음 기록된 점수라면, 점수 총 합 / 갯수
        if (scoreOptional.isPresent()) {
            average = scoreOptional.get();
            isCached = 0.6d;
        }
        average = average * (1 - isCached) + (numerator / denominator) * isCached;

        // 이전에 캐싱된 기록이 있다면, db에 사용자 추천 목록으로 저장.
        if (scoreOptional.isPresent()) {
            double finalAverage = average;

            // 게시글별 점수 리스트 계산된 평균보다 높은 점수 리스트만 추출
            List<ScoreDto> scoreList = scoreDtoList.stream().filter(s -> s.getScore() > finalAverage).collect(Collectors.toList());
            Set<Recommend> recommends = new HashSet<>();

            // 사용자 추천 리스트 갱신
            // TODO: 너무 많은 DB 조회
            //  있는 데이터만 가져와서 filter 처리로 빼고, 없는 데이터만 추가할 쿼리로 변경 필요.
            for (ScoreDto scoreDto : scoreList) {
                // 점수에 해당하는 게시글이 이미 사용자 추천 게시글에 존재하는지 확인
                Optional<PostInfo> relationCheck = Optional.ofNullable(postInfoRepository.existsRecommended(scoreDto.getPostId(), userId));

                // 새로 생성된 점수라면 추천 리스트에 추가.
                if(relationCheck.isEmpty()){
                    PostInfo postInfo = postInfoRepository.findNodeById(scoreDto.getPostId()).orElseThrow(RuntimeException::new);
                    Recommend recommend = new Recommend(postInfo, scoreDto);
                    recommends.add(recommend);
                }
            }

            // 사용자 추천 리스트 추가 후 저장.
            UserInfo userInfo = userInfoRepository.findNodeById(userId).orElseThrow(RuntimeException::new);
            userInfo.getRecommends().addAll(recommends);
            userInfoRepository.save(userInfo);
        }
        // 캐시에 평균 값 저장
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
