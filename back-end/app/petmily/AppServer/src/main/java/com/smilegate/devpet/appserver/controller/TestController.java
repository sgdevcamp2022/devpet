package com.smilegate.devpet.appserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("pingpong")
    public String pingpong()
    {
        return "yes I'm pong";
    }
}
