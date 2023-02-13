package com.devpet.feed.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class LikePostDto {
    private String userId;
    private String postId;
}
