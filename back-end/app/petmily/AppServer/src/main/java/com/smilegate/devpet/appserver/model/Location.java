package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "location")
public class Location {
    public static enum Category {FACILITY, GROUP, PRIVATE}

    @Field
    @Id
    private Long locationId;
    @Field
    private String name;
    @Field
    private String address;
    @Field
    private Long category = (long)(Category.PRIVATE.ordinal());
    @Field
    private Double longitude;
    @Field
    private Double latitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(locationId, location.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId);
    }
}
