package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@RequiredArgsConstructor
@Getter @Setter
public class FeedRequest {
    private String content;
    private Long category;
    private Location coord;
    private ArrayList<Long> tag;
    private Long groupId;
    private ArrayList<String> imageUrl;
//    private ArrayList<Long> hashTag;
}
