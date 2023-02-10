package com.devpet.feed.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScoreDto {

    private String userId;

    private String postId;

    private double score;

}
