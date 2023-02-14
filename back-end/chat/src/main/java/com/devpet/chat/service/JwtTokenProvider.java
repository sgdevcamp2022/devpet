package com.devpet.chat.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;


    /**
     * Jwt Token을 복호화 하여 이름을 얻는다.
     */


    public String getEmail(final String jwt) {

        final String payloadJWT = jwt.split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();

        final String payload = new String(decoder.decode(payloadJWT));
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonArray = jsonParser.parseMap(payload);

        if (!jsonArray.containsKey("user_name")) {
            throw new IllegalStateException("유요하지 않은 AccessToken 입니다");
        }

        return jsonArray.get("user_name").toString();
    }
}
