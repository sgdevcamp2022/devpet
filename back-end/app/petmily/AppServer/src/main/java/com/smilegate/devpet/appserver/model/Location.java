package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
}
