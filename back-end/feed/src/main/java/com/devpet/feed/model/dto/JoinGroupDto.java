package com.devpet.feed.dto;

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
