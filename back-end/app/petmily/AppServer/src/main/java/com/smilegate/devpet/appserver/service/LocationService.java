package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    public Location postLocation(Location location)
    {
        Location returnLocation = locationRepository.findByCoordAndCategory(new Point(location.getCoord().getX(),
                location.getCoord().getY()),
                location.getCategory())
                .orElseGet(()->locationRepository.save(location));
        return returnLocation;
    }
    public List<Location> getNearByLocation(Point point, Double distance, long category)
    {
        return locationRepository.findByCategoryAndCoordWithin(point,distance,category);
    }
}
