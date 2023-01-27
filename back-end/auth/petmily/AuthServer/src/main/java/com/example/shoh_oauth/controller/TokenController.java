package com.example.shoh_oauth.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    @GetMapping("/token/expired")
    public String auth() {
        throw new RuntimeException();
    }

//    @GetMapping("/token/refresh")
//    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getHeader("Refresh"); // 사용자가 서버에 보낼 Refresh 토큰 이름 서버는 이걸 가지고 토큰을 뽑아냄
//
////        if (token != null && tokenService.verifyToken(token)) {
////            String email = tokenService.getUid(token);
////            Token newToken = tokenService.generateToken(email, "USER");
////
////            response.addHeader("Auth", newToken.getToken());
////            response.addHeader("Refresh", newToken.getRefreshToken());
////            response.setContentType("application/json;charset=UTF-8");
//
//            return "HAPPY NEW TOKEN";
//        }
//
//        //throw new RuntimeException();
//        return "HAPPY NEW TOKEN";
//    }



    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}