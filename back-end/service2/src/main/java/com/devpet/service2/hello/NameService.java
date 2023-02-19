package com.devpet.service2.hello;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class NameService {

    public String getName(String id) {
        switch (id) {
            case "1":
                return "Jesse";
            case "2":
                return "Jimmy";
            default:
                return "UnKnown";
        }
    }
}
