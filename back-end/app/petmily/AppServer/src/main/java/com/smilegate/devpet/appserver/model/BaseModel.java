package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter @Setter
@RequiredArgsConstructor
public abstract class BaseModel {
    @Field
    private boolean isUsed;

    @Field
    @CreatedBy
    private LocalDateTime createdAt;
    @Field
    @LastModifiedBy
    private LocalDateTime updatedAt;
}
