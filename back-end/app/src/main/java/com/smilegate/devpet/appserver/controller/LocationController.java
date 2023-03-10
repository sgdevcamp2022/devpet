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

import static com.smilegate.devpet.appserver.config.ConstVariables.LONGITUDE_RADIUS;
import static com.smilegate.devpet.appserver.config.ConstVariables.MILE_TO_METER;

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
        Circle center = null;
        if (longitude!=null&&latitude!=null&&distance!=null)
            center = new Circle(longitude,latitude,distance/(LONGITUDE_RADIUS*MILE_TO_METER));
        return locationService.getNearByLocation(center, category, content,address);
    }
}
