package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.FavoriteRequest;
import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.Favorite;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.FavoriteRepository;
import com.smilegate.devpet.appserver.repository.mongo.PetRepository;
import com.smilegate.devpet.appserver.repository.redis.FavoriteRedisRepository;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteRedisRepository favoriteRedisRepository;
    private final NewPostRedisRepository newPostRedisRepository;
    private final SequenceGeneratorService favoriteSequenceGeneratorService;
    private final MongoOperations mongoOperations;
    private final KafkaProducerService kafkaProducerService;
    public Favorite postFavorite(Favorite favorite)
    {
        favorite.setFavoriteId(favoriteSequenceGeneratorService.longSequenceGenerate(Favorite.SEQUENCE_NAME));
        Favorite result = favoriteRepository.save(favorite);
        return result;
    }

    /**
     * 좋아요 정보를 레디스에 캐싱합니다.
     * (캐싱된 데이터는 10초마다 일괄 처리 됩니다.)
     * @param feedId 게시글 아이디
     * @param favoriteRequest 좋아요 정보
     * @param userInfo 사용자 정보
     * @return 정상 저장 여부
     */
    public boolean setFeedFavorite(long feedId, FavoriteRequest favoriteRequest, UserInfo userInfo)
    {
        favoriteRedisRepository.save(feedId, userInfo.getUserId(),favoriteRequest.isFavorite());
        return true;
    }
    /**
     * 게시글을 좋아요 하고 있는 사람들의 피드 캐시에 데이터를 저장합니다.
     * @param feedId 게시글 정보
     */
    public void favoriteUserAddFeed(Long feedId)
    {
        List<Favorite> favoriteList = favoriteRepository.findAllByPostIdAndFavorite(feedId,true);
        if (favoriteList==null || favoriteList.isEmpty())
            return;
        for(Long userId : favoriteList.stream().map(Favorite::getUserId).collect(Collectors.toList()))
            newPostRedisRepository.save(userId,feedId);
    }

    /**
     * favorite 정보를 일괄 저장합니다.
     * @param favoriteList 저장할 favorite 정보 리스트
     * @return 저장된 favorite 정보 리스트
     */
    public List<Favorite> postAllFavorite(List<Favorite> favoriteList)
    {
        // favorite 리스트에서 id가 null인 정보를 제거합니다.
        List<Favorite> favoriteStream = favoriteList.stream().filter(item->item.getFavoriteId()==null).collect(Collectors.toList());

        // 설정할 아이디의 마지막 번호를 가져옵니다.
        AtomicLong lastSeq = new AtomicLong(favoriteSequenceGeneratorService.longSequenceBulkGenerate(Feed.SEQUENCE_NAME, (int) favoriteStream.size()));

        // 각 정보별로 id를 설정합니다.
        favoriteStream.forEach(item->{
            item.setFavoriteId(lastSeq.get());
            lastSeq.getAndIncrement();
        });

        // bulk insert를 연산한 결과값을 가져옵니다.
        List<Favorite> result = new ArrayList<>(mongoOperations.insert(favoriteList,"favorite"));

        // kafka 전송
        kafkaProducerService.feedFavoriteSend(favoriteList);
        return result;
    }
}
