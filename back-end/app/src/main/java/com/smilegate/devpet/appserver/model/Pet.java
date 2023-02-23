package com.smilegate.devpet.appserver.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@Getter @Setter
@RedisHash("pet")
@Document(collection = "pet")
public class Pet extends BaseModel{
    @Transient
    public static final String SEQUENCE_NAME = "pet_sequence";

    @Id
    private Long petId;
    private Long profileId;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetDat;
    private String division;
    private String about;
    private String imageUrl;

    @Transient
    private Set<Tag> tags;
}
