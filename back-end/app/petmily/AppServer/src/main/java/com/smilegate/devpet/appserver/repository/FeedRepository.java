package com.smilegate.devpet.appserver.repository;


import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.Location;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends MongoRepository<Feed, Long> {
    final String FEED_NEAR_QUERY = "{ " +
                "'content' : {'$regex':  ?3}," +
                "'location' : { " +
                    "'category':{'$eq': ?2},"+
                    "'coord':{" +
                        "'$nearSphere': {" +
                            "'$geometry': {" +
                                "'type': 'Point'," +
                                "'coordinates': ?0}," +
                                "'$minDistance': 0," +
                                "'$maxDistance': ?1}" +
                            "}" +
                        "}" +
                    "}";
    @Query(FEED_NEAR_QUERY)
    List<Feed> findByNear(Point center, long distance, int category, String word, PageRequest pageRequest);
    List<Feed> findByLocationAndContent(Location location, String word, Pageable pageable);
}
