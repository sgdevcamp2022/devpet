package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class CommentRequest {
    private Long commentId;
    private Long profileId;
    private String comment;
}
