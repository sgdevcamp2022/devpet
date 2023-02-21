package com.smilegate.devpet.appserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRelationRequest {
    private Long id;
    private PostInfo postInfo;
}
