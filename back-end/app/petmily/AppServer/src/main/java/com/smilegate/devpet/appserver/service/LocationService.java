package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.repository.mongo.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoEntityInformation<Location,Long> locationEntityInformation;
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
    public List<Location> getNearByLocation(Point point, Double distance, long category)
    {
        return locationRepository.findByCategoryAndCoordWithin(point,distance,category);
    }

    /**
     * 위치 정보 리스트에서 db에 없는 항목만 저장
     * @param locations : 저장하려는 위치 정보 리스트
     * @return 저장된 location 리스트 반환
     */
    public List<Location> saveAll(List<Location> locations)
    {

        Stream<Location> locationStream = locations.stream().filter(item->!locationEntityInformation.isNew(item));
        if (locationStream.findAny().isPresent())
        {
            return locations;
        }
        long lastSeq = sequenceGeneratorService.longSequenceBulkGenerate(Location.SEQUENCE_NAME,(int) locationStream.count());
        AtomicLong seq = new AtomicLong(lastSeq - locationStream.count() + 1);
        locationStream.forEach(item->{
            item.setLocationId(seq.get());
            seq.getAndIncrement();
        });
        List<Location> result =  new ArrayList<>(locationMongoOperation.insert(locationStream.collect(Collectors.toList()),"location"));
        return result;
    }
}
