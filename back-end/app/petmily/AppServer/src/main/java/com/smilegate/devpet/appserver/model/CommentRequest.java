package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class CommentRequest {
    private Profile userProfile;
    private String comment;
}
