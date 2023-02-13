package com.devpet.feed.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JoinGroupDto {
    private String userId;
    private String groupName;
}
