package com.devpet.feed.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowDto {

    // 구독자
    private String follower;

    // 내가 구독
    private String following;


}