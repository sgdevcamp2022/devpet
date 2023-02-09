package com.example.oauth.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String my;

    private String name;

    // 이메일
    private String username;

    private String nickname;

    private ArrayList<String> imageUrl;

    @Builder
    public Profile(String my, String name, String username, String nickname, ArrayList<String> imageUrl) {
        this.my = my;
        this.name = name;
        this.username = username;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

}