package com.devpet.feed.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDto {

    // 구독자
    private String userId;

    // 내가 구독
    private String postId;
}