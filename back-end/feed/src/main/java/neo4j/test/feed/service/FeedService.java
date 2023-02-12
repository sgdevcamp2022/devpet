package neo4j.test.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo4j.test.feed.common.Hash;
import neo4j.test.feed.model.dto.ScoreDto;
import neo4j.test.feed.model.entity.PostInfo;
import neo4j.test.feed.model.entity.UserInfo;
import neo4j.test.feed.model.relationship.Recommend;
import neo4j.test.feed.repository.PostInfoRepository;
import neo4j.test.feed.repository.RedisRepository;
import neo4j.test.feed.repository.UserInfoRepository;
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
                    PostInfo postInfo = postInfoRepository.findNodeById(scoreDto.getPostId());
                    Recommend recommend = new Recommend(postInfo, scoreDto);
                    recommends.add(recommend);
                }
            }

            UserInfo userInfo = userInfoRepository.findByUserId(userId);
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
}
