package com.smilegate.devpet.appserver.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikePostDto {
    private String userId;
    private String postId;
    private Boolean isFavorite;
}
