package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter @Setter
@RequiredArgsConstructor
@RedisHash("profile")
@Document(collection = "profile")
public class Profile extends BaseModel{
    @Transient
    public static final String SEQUENCE_NAME = "profile_sequence";
    @Id
    private Long profileId;
    @Field
    private Long userId;
    @Field
    private String nickname;
    @Field
    private String about;
    @Field
    private LocalDate birth;
    @Field
    private ArrayList<Pet> petList;
    @Transient
    private long follower;
    @Transient
    private long follow;
    private String imageUrl;

    public Profile(ProfileRequest profileRequest,UserInfo userInfo)
    {
        this.setProfileData(profileRequest);
        this.userId = userInfo.getUserId();
    }

    public Profile(ProfileRequest profileRequest,Long userId)
    {
        this.setProfileData(profileRequest);
        this.userId = userId;
    }
    public void setProfileData(ProfileRequest profileRequest)
    {
        if (profileRequest.getId()!=null)
            this.profileId = profileRequest.getId();
        this.nickname = profileRequest.getNickname();
        this.about = profileRequest.getAbout();
        this.birth = profileRequest.getBirth();
        this.petList = profileRequest.getPetList();
        this.imageUrl = profileRequest.getImageUrl();
    }
}
