package com.example.shoh_oauth.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String username;

    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String name;

    @Size(min = 1, max = 20, message = "닉네임이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickname;

    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{6,20}", message = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String password;

    private String age;

    private String gender;

    private String phone;

    private String provider;

    @Builder
    public SignUpRequest(String username, String name ,String nickname, String password, String age, String gender, String phone, String provider) {
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.provider = provider;
    }
}
