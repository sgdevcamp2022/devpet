package com.smilegate.devpet.appserver.service;


import com.smilegate.devpet.appserver.api.relation.FeedApi;
import com.smilegate.devpet.appserver.api.relation.PostInfoApi;
import com.smilegate.devpet.appserver.api.relation.UserInfoApi;
import com.smilegate.devpet.appserver.model.*;
import com.smilegate.devpet.appserver.repository.mongo.FeedRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.RecommendPostRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final MongoTemplate mongoTemplate;
    private final FeedRepository feedRepository;
    private final LocationService locationService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations mongoOperations;
//    private final KafkaProducerService kafkaProducerService;
    private final NewPostRedisRepository newPostRedisRepository;
    private final RecommendPostRedisRepository recommendPostRedisRepository;
    private final FeedApi relationFeedService;
    private final UserInfoApi userInfoService;
    private final PostInfoApi relationPostService;
    private final ProfileService profileService;
    /**
     * 게시글 데이터를 저장합니다.
     * @param feedRequest 저장할 게시글 데이터
     * @param userInfo 사용자 정보
     * @return db 에 저장된 게시글 데이터
     */
    public Feed postFeed(FeedRequest feedRequest, UserInfo userInfo)
    {
        Profile profile = profileService.getProfile(userInfo);
        // 저장할 게시글 데이터 생성
        Feed feed = new Feed(feedRequest,profile.getProfileId());

        // 게시글 데이터 아이디 설정
        feed.setFeedId(sequenceGeneratorService.longSequenceGenerate(Feed.SEQUENCE_NAME));

        // 위치 정보 설정
        locationService.postLocation(feed.getLocation());

        // db에 게시글 저장.
        feed = feedRepository.save(feed);

        // 게시물 캐시에 내가 작성한 게시물 추가.
        newPostRedisRepository.save(userInfo.getUsername(), feed.getFeedId());
//        // 저장된 게시물 관계 연산 서버로 전송
        PostInfoDto postInfoDto = PostInfoDto.builder()
                .postId(feed.getFeedId().toString())
                .postCategory(feed.getLocation().getCategory().toString())
                .userId(userInfo.getUsername())
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
    public Feed putFeed(FeedRequest feedRequest,Long feedId,UserInfo userInfo)
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
                .userId(userInfo.getUsername().toString())
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
        List<Feed> result = findNearFeedList(word,category,circle,pageRequest);
        return  result.stream().map((feed)->{
            if (feed.getImageUrl().size()<1)
                return null;
            return feed.getImageUrl().get(0);
        }).collect(Collectors.toList());
    }

    public List<String> getMyFeedList(UserInfo userInfo, int start, int count)
    {

        PageRequest pageRequest = PageRequest.of(start/count,count);
        List<Feed> result = feedRepository.findByProfileIdOrderByCreatedAtDesc(userInfo.getUserId());
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
    public List<Feed> getMarkerFeedList(Point center, Integer category, String word, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        Circle circle = new Circle(center,1);
        return findNearFeedList(word, category,circle, pageRequest);
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
    public List<Feed> getFeedList(String word, Integer category, Circle circle, int start, int size)
    {
        PageRequest pageRequest = PageRequest.of(start/size,size);
        return findNearFeedList(word,category,circle,pageRequest);
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
        // 피드 서버에서 사용자가 조회할 피드 리스트를 꺼내 옵니다.
        List<Long> feedIds = userInfoService.getFollowPostList(userInfo.getUsername()).stream().map(Long::parseLong).collect(Collectors.toList());

        // 캐시에 가져온 데이터 저장 합니다.
        newPostRedisRepository.saveAll(userInfo.getUsername(),feedIds);
        saveRecommendUserPostList(userInfo);

        // 캐시에서 게시글을 20개 꺼내 옵니다.
        List<Long> postIds = newPostRedisRepository.findById(userInfo.getUsername(), 20);

        // 만약 사용자 관계 관련 게시글이 20개보다 적다면 추천 게시글 리스트를 받아옵니다.
        if (postIds.size()<20)
        {
            postIds.addAll(recommendPostRedisRepository.findById(userInfo.getUsername(), 20- postIds.size()));
        }
        Set<Long> postIdSet = new HashSet<>(postIds);
        // db에서 현재 사용되고 있는 게시글 20개를 꺼내옵니다.
        return Streamable.of(feedRepository.findAllByFeedIdInAndIsUsedIsTrue(postIdSet)).stream().collect(Collectors.toList());
    }

    /**
     * 관계 서버에서 받아온 사용자 추천 게시물을 캐시에 저장합니다.
     * @param userInfo 사용자 정보
     */
    @Transactional
    public void saveRecommendUserPostList(UserInfo userInfo)
    {
        // 사용자 팔로우기반 게시글 추천을 받아와 저장합니다.
        HashSet<Long> resultSet = relationFeedService.getPostList(userInfo.getUsername()).stream().map(Long::parseLong).collect(Collectors.toCollection(HashSet::new));

        // 사용자 관심 기반 게시글을 추천 받아와 저장합니다.
        resultSet.addAll(userInfoService.getPetLikeCommentPostList(userInfo.getUsername()).stream().map(Long::parseLong).collect(Collectors.toCollection(HashSet::new)));
        resultSet.addAll(userInfoService.getRecommendedFollowPostList(userInfo.getUsername()).stream().map(Long::parseLong).collect(Collectors.toCollection(HashSet::new)));

        // 캐시에 사용자 추천 게시글 저장.
        recommendPostRedisRepository.saveAll(userInfo.getUsername(),resultSet);
    }

    private List<Feed> findNearFeedList(String content, Integer category,Shape shape, Pageable pageable)
    {
        Query query = new Query();
        if (content != null)
            query.addCriteria(Criteria.where("content").regex(content));
        if (category != null)
            query.addCriteria(Criteria.where("location.category").is(category));
        if (shape != null)
            query.addCriteria(Criteria.where("location.coord").within(shape));
        query.with(pageable);
        List<Feed> result = mongoTemplate.find(query, Feed.class);
        return result;
    }
}
