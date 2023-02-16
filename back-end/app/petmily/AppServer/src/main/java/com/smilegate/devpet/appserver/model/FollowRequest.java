package com.smilegate.devpet.appserver.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowRequest {

    // 구독자
    private String follower;

    // 내가 구독
    private String following;


}