package com.smilegate.devpet.appserver.repository.mongo;


import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.Location;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends MongoRepository<Feed, Long> {
//    @Query("{ \"content\" : { \"$regex\" : :#{#content}}, \"location.category\" : :#{#category} , \"location.coord\" : { \"$geoWithin\" : { \"$center\" : [[:#{#shape.center.x}, :#{#shape.center.y}], :#{#shape.radius.value}]}}}")
    List<Feed> findByContentRegexAndLocationCategoryAndLocationCoordWithin(@Param("content") String content, @Param("category") int category,@Param("shape") Shape shape,Pageable pageable);

    List<Feed> findByLocationAndContent(Location location, String word, Pageable pageable);
}
