package com.smilegate.devpet.appserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class FeedCommentKafkaRequest {
    private Long feedId;
    private Long userId;
    private String comment;

    public FeedCommentKafkaRequest(Long feedId, String comment, Long userId) {
        this.feedId = feedId;
        this.comment = comment;
        this.userId = userId;
    }
}
