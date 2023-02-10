package com.devpet.feed.service;

import com.devpet.feed.data.dto.ScoreDto;
import com.devpet.feed.data.node.PostInfo;
import com.devpet.feed.data.node.UserInfo;
import com.devpet.feed.data.relationship.Recommended;
import com.devpet.feed.exception.DuplicateUserException;
import com.devpet.feed.repository.PostRepository;
import com.devpet.feed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void firstAverage(List<ScoreDto> scoreList) {

        double average = 0;

        for (ScoreDto data : scoreList) {
            average += data.getScore();
        }
        average = (average / scoreList.size());
        average = Math.round(average * 10) / 10.0;
    }

    public void createRecommend(List<ScoreDto> scoreList) {

        // redis 에서 이전 평균값 가져오기
        double previousAverage = 3.7;
        double currentAverage = 0;
        double criteria = 0;


        for (ScoreDto data : scoreList) {
            currentAverage += data.getScore();
            if (data.getScore() > previousAverage) {
                recommended(data);
            }
        }
        currentAverage = (currentAverage / scoreList.size());
        currentAverage = Math.round(currentAverage * 10) / 10.0;

        // redis 에 저장
        criteria = previousAverage * 0.4 + currentAverage * 0.6;
    }


    public void recommended(ScoreDto scoreDto) {

        UserInfo userInfo = userRepository.findByUserId(scoreDto.getUserId());
        PostInfo postInfo = postRepository.findByPostId(scoreDto.getPostId());

        Set<Recommended> user = userInfo.getRecommended();
        for (Recommended entity : user) {
            if (entity.getPostInfo().getPostId().equals(postInfo.getPostId())) {
                throw new DuplicateUserException("이미 존재하는 계정입니다.");
            }
        }

        Recommended recommended = new Recommended(postInfo);
        recommended.setScore(scoreDto.getScore());
        userInfo.getRecommended().add(recommended);

        userRepository.save(userInfo);
    }

    public List<String> getPostList(String userId) {
        return userRepository.getPostList(userId);
    }
}