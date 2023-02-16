package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.repository.mongo.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class LocationService {
    private final MongoTemplate mongoTemplate;
    private final LocationRepository locationRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations locationMongoOperation;
    public Location postLocation(Location location)
    {
        Location returnLocation = locationRepository.findByCoordAndCategory(new Point(location.getCoord().getX(),
                location.getCoord().getY()),
                location.getCategory())
                .orElseGet(()->{
                    location.setLocationId(sequenceGeneratorService.longSequenceGenerate(Location.SEQUENCE_NAME));
                    return locationRepository.save(location);
                });
        return returnLocation;
    }
    public List<Location> getNearByLocation(Circle circle, Integer category,String content,String address)
    {
        return getMarkers(content, address, category, circle);
    }
    public List<Location> getMarkers(String content, String address, Integer category, Circle circle)
    {
        Query query = new Query();
        if (address != null)
            query.addCriteria(Criteria.where("address").regex(content));
        if (content != null)
            query.addCriteria(Criteria.where("name").regex(content));
        if (category != null)
            query.addCriteria(Criteria.where("category").is(category));
        if (circle != null)
            query.addCriteria(Criteria.where("coord").within(circle));
        List<Location> result = mongoTemplate.find(query, Location.class);
        return result;
    }

    /**
     * 위치 정보 리스트에서 db에 없는 항목만 저장
     * @param locations : 저장하려는 위치 정보 리스트
     * @return 저장된 location 리스트 반환
     */

    public List<Location> saveAll(List<Location> locations)
    {

        List<Location> locationStream = locations.stream().filter(item->item.getLocationId()==null).collect(Collectors.toList());
//        MongoEntityInformation<Location,Long> locationEntityInformation = factory.getEntityInformation(Location.class);
//        Stream<Location> locationStream = locations.stream().filter(item->!locationEntityInformation.isNew(item));
        if (locationStream.isEmpty())
        {
            return locations;
        }
        long lastSeq = sequenceGeneratorService.longSequenceBulkGenerate(Location.SEQUENCE_NAME,(int) locationStream.size());
        AtomicLong seq = new AtomicLong(lastSeq - locationStream.size() + 1);
        locationStream.forEach(item->{
            item.setLocationId(seq.get());
            seq.getAndIncrement();
        });
        List<Location> result =  new ArrayList<>(locationMongoOperation.insert(locationStream,"location"));
        return result;
    }
}
