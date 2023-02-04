package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/maps")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @GetMapping
    public List<Location> getNearByLocation(@RequestParam("latitude") Double latitude
                                            ,@RequestParam("longitude") Double longitude
                                            ,@RequestParam("distance") Double distance
                                            ,@RequestParam("category") long category)
    {
        return locationService.getNearByLocation(new Point(latitude,longitude),distance,category);
    }
}
