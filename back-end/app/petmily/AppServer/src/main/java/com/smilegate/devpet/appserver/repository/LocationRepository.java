package com.smilegate.devpet.appserver.repository;

import com.smilegate.devpet.appserver.model.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location,Long> {

    //TODO: 거리 단위(시, 동, 면, 읍)로 가져올 방법 구상.
    public List<Location> findByLatitudeAndLongitude(Double latitude, Double longitude, Pageable pageable);
    public Location findByLatitudeAndLongitudeAndCategory(Double latitude, Double longitude ,long category, Pageable pageable);
}
