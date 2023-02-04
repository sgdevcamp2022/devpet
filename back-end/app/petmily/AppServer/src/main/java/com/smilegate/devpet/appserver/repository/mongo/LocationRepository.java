package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.Location;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface LocationRepository extends MongoRepository<Location,Long> {
    final String LOCATION_NEAR_BY_QUERY = "{ '$geoNear' : { '$elemMatch' : { 'city' : { '$eq' : 'lnz' } } } }";
    public Optional<Location> findByCoordAndCategory(Point coord, long category);

    //TODO: 거리 단위(시, 동, 면, 읍)로 가져올 방법 구상.
    public List<Location> findByCategoryAndCoordWithin(Point coord, Double distance, long category);
}
