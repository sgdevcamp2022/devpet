package com.devpet.feed.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {

    // 구독자
    private String follower;

    // 내가 구독
    private String following;


}