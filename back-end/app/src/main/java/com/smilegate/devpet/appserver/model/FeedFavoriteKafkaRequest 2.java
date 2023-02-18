package com.smilegate.devpet.appserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class FeedFavoriteKafkaRequest {
    private Long feedId;
    private Long userId;
    private boolean isFavorite;

    public FeedFavoriteKafkaRequest(Long feedId, boolean isFavorite, Long userId) {
        this.feedId = feedId;
        this.isFavorite = isFavorite;
        this.userId = userId;
    }
}
