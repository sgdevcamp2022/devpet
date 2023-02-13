package com.smilegate.devpet.appserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiredArgsConstructor
@Getter @Setter
public class ProfileRequest {
    private Long id;
    private String nickname;
    private String about;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private ArrayList<Pet> petList;
}
