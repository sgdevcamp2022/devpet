package com.example.other;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//TODO 암호화 추가
@Service
public class EncoderService implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
