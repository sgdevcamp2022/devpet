package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@RequiredArgsConstructor
@Getter @Setter
public class FeedRequest {
    private String content;
    private String category;
    private Location location;
    private ArrayList<Long> tagUsers;
    private Long groupId;
    private ArrayList<String> imageUrl;
    private ArrayList<String> hashTag;
}
