package com.smilegate.devpet.appserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
@Builder
public class FeedFavoriteKafkaRequest {
    private Long feedId;
    private Long userId;
    private Long isFavorite;

    public FeedFavoriteKafkaRequest(Long feedId, boolean isFavorite, Long userId) {
    }
}
