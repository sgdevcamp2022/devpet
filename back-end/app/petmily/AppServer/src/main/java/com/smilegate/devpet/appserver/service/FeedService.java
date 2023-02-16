package com.smilegate.devpet.appserver.service;


import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.repository.mongo.FeedRepository;
import com.smilegate.devpet.appserver.repository.redis.FavoriteRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final LocationService locationService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations mongoOperations;
//    private final KafkaProducerService kafkaProducerService;
    private final NewPostRedisRepository newPostRedisRepository;
    private final com.smilegate.devpet.appserver.api.relation.FeedService relationFeedService;
    private final com.smilegate.devpet.appserver.api.relation.PostInfoService relationPostService;
    /**
     * 게시글 데이터를 저장합니다.
     * @param feedRequest 저장할 게시글 데이터
     * @param userInfo 사용자 정보
     * @return db 에 저장된 게시글 데이터
     */
    public Feed postFeed(FeedRequest feedRequest, UserInfo userInfo)
    {
        // 저장할 게시글 데이터 생성
        Feed feed = new Feed(feedRequest,userInfo);

        // 게시글 데이터 아이디 설정
        feed.setFeedId(sequenceGeneratorService.longSequenceGenerate(Feed.SEQUENCE_NAME));

        // 위치 정보 설정
        locationService.postLocation(feed.getLocation());

        // db에 게시글 저장.
        feed = feedRepository.save(feed);

        // 게시물 캐시에 내가 작성한 게시물 추가.
        newPostRedisRepository.save(userInfo.getUserId(), feed.getFeedId());

//        // 저장된 게시물 관계 연산 서버로 전송
        PostInfoDto postInfoDto = PostInfoDto.builder()
                .postId(feed.getFeedId().toString())
                .postCategory(feed.getLocation().getCategory().toString())
                .userId(feed.getUserId().toString())
                .createdAt(feed.getCreatedAt().toString())
                .tags(feed.getHashTags()).build();
        relationPostService.savePostInfo(postInfoDto);
        return feed;
    }

    /**
     * 게시글 정보를 수정합니다.
     * @param feedRequest 수정할 게시글 정보.
     * @param feedId 수정할 게시글 아이디.
     * @return db에 수정된 게시글 데이터
     */
    public Feed putFeed(FeedRequest feedRequest,Long feedId)
    {
        // 게시글 아이디로 게시글 데이터를 가져옵니다.
        Feed feed = feedRepository.findById(feedId).orElseThrow(NullPointerException::new);

        // 수정된 게시글 정보로 변경합니다.
        feed.setFeedData(feedRequest);

        // 위치가 바뀌었다면 위치 정보를 새로 저장합니다.
        if (!feed.getLocation().equals(feedRequest.getLocation()))
            locationService.postLocation(feedRequest.getLocation());

        // 수정된 게시글 정보를 저장합니다.
        feedRepository.save(feed);


        // 수정된 게시물 관계 연산 서버로 전송
        PostInfoDto postInfoDto = PostInfoDto.builder()
                .postId(feed.getFeedId().toString())
                .postCategory(feed.getLocation().getCategory().toString())
                .userId(feed.getUserId().toString())
                .createdAt(feed.getCreatedAt().toString())
                .tags(feed.getHashTags()).build();
        relationPostService.savePostInfo(postInfoDto);
        return feed;
    }

    /**
     * 게시글을 삭제 합니다.
     * @param feedId 게시글 아이디
     * @return 삭제된 게시글 정보
     */
    public Feed deleteFeed(Long feedId)
    {
        // 게시글 아이디로 게시글 정보를 가져옵니다.
        Feed feed = feedRepository.findById(feedId).orElseThrow(NullPointerException::new);

        // 실제로 데이터를 삭제하지 않고 usedYn을 false로 바꾸고 저장합니다.
        feed.setUsed(false);
        feedRepository.save(feed);
        return feed;
    }

    /**
     * 이미지로만 이루어진 게시글 데이터를 조회합니다.
     * @param word 조회 단어
     * @param category 조회 분류(0:개인,1:그룹,2:시설)
     * @param circle 반경
     * @param start 시작 번호
     * @param size 조회 갯수
     * @return 조회한 게시글 이미지 리스트
     */
    public List<String> getSimpleFeedList(String word, int category, Circle circle, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        List<Feed> result = feedRepository.findByContentRegexAndLocationCategoryAndLocationCoordWithinAndUsedIsTrue(word,category,circle,pageRequest);
        return  result.stream().map((feed)->{
            if (feed.getImageUrl().size()<1)
                return null;
            return feed.getImageUrl().get(0);
        }).collect(Collectors.toList());
    }
    /**
     * 마커에 해당하는 게시글 리스트를 조회합니다.
     * @param center 조회할 마커 위치
     * @param category 조회 분류(0:개인,1:그룹,2:시설)
     * @param word 조회 단어
     * @param start 시작 번호
     * @param size 조회 갯수
     * @return 조회한 게시글 정보 리스트
     */
    public List<Feed> getMarkerFeedList(Point center, int category, String word, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        Location location = new Location();
        location.setCoord(center);
        location.setCategory((long)category);
        return feedRepository.findByLocationAndContentAndUsedIsTrue(location,word, pageRequest);
    }

    /**
     * 사용자 중짐을 반경으로 게시글 정보를 조회합니다.
     * @param word 조회할 단어
     * @param category 분류(0:개인,1:그룹,2:시설)
     * @param circle 반경
     * @param start 시작 번호
     * @param size 조회 갯수
     * @return 조회한 게시글 정보 리스트
     */
    public List<Feed> getFeedList(String word, int category, Circle circle, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        return feedRepository.findByContentRegexAndLocationCategoryAndLocationCoordWithinAndUsedIsTrue(word,category,circle,pageRequest);
    }

    /**
     * 게시글 정보를 일괄 저장합니다.
     * @param pushList 저장할 게시글 정보 리스트
     * @return 저장된 게시글 정보 리스트
     */
    public List<Feed> postAllFeed(List<Feed> pushList) {
        List<Location> pushLocations = pushList.stream().map(Feed::getLocation).filter(Objects::nonNull).collect(Collectors.toList());
        locationService.saveAll(pushLocations);
        List<Feed> feedStream = pushList.stream().filter(item->item.getFeedId()==null).collect(Collectors.toList());
        AtomicLong lastSeq = new AtomicLong(sequenceGeneratorService.longSequenceBulkGenerate(Feed.SEQUENCE_NAME, (int) feedStream.size()));
        feedStream.forEach(item->{
            item.setFeedId(lastSeq.get());
            lastSeq.getAndIncrement();
        });
        List<Feed> result = new ArrayList<>(mongoOperations.insert(pushList,"feed"));
        return result;
    }

    /**
     * 피드 캐싱된 데이터 + 추천 게시글 리스트를 조회합니다.
     * @param userInfo 사용자 정보
     * @return 조회된 게시글 정보
     */
    public List<Feed> getFeedList(UserInfo userInfo)
    {
        //TODO 관계 데이터와 추천 데이터 구분 필요

        // 피드 서버에서 사용자가 조회할 피드 리스트를 꺼내 옵니다.
        List<Long> feedIds = relationFeedService.getPostList(userInfo).stream().map(Long::parseLong).collect(Collectors.toList());

        // 캐시에 가져온 데이터 저장 합니다.
        newPostRedisRepository.saveAll(userInfo.getUserId(),feedIds);

        // 캐시에서 게시글을 20개 꺼내 옵니다.
        List<Long> postIds = newPostRedisRepository.findById(userInfo.getUserId(), 20);

        // db에서 현재 사용되고 있는 게시글 20개를 꺼내옵니다.
        return Streamable.of(feedRepository.findAllByFeedIdInAndUsedIsTrueOrderByUpdatedAtDesc(postIds)).stream().collect(Collectors.toList());
    }
}
