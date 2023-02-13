package com.example.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/api/token")
    public ResponseEntity<?> apiTest()  {

        return ResponseEntity.ok("api test good");
    }

    @GetMapping(value = "/users/user")
    public ResponseEntity<?> user()  {

        return ResponseEntity.ok("user test good");
    }

}
