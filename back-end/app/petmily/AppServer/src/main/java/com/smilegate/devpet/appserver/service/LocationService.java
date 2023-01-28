package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    public Location postLocation(Location location)
    {
        Location returnLocation = locationRepository.findByLatitudeAndLongitudeAndCategory(location.getLatitude(),
                location.getLongitude(),
                location.getCategory(),null)
                .orElseGet(()->locationRepository.save(location));
        return returnLocation;
    }
    public Location getNearByLocation()
    {
        return null;
    }
}
