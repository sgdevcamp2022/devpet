package com.smilegate.devpet.appserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@RedisHash("favorite")
@Document("favorite")
public class Favorite {
    @Transient
    public static final String SEQUENCE_NAME = "favorite_sequence";
    private Long favoriteId;
    private Long postId;
    private Long userId;
    private boolean isFavorite;
    @Field
    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();
    public Favorite(Long postId,boolean isFavorite, Long userId)
    {
        this.postId = postId;
        this.isFavorite = isFavorite;
        this.userId = userId;
    }
}
