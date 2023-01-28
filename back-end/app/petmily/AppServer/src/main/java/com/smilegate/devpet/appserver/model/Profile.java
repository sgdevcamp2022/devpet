package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter @Setter
@RequiredArgsConstructor
@Document(collection = "profile")
public class Profile extends BaseModel{
    @Id
    private long profileId;
    @Field
    private UserInfo userInfo;
    @Field
    private String nickname;
}
