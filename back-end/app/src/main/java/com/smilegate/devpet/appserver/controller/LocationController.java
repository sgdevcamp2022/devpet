package com.smilegate.devpet.appserver.controller;

import com.smilegate.devpet.appserver.model.Location;
import com.smilegate.devpet.appserver.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @GetMapping("/markers")
    public List<Location> getNearByLocation(@RequestParam("latitude") Double latitude
                                            ,@RequestParam("longitude") Double longitude
                                            ,@RequestParam("distance") Double distance
                                            ,@RequestParam(value="category",required = false) Integer category
                                            ,@RequestParam(value="content",required = false) String content
                                            ,@RequestParam(value="address",required = false) String address)
    {
        return locationService.getNearByLocation(new Circle(latitude,longitude,distance), category, content,address);
    }
}
