package com.smilegate.devpet.appserver.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScoreRequest {
    private String userId;
    private String postId;
    private double score;
}
