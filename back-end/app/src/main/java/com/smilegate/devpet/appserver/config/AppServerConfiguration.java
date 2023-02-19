package com.smilegate.devpet.appserver.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @apiNote intercepter 또는 ArgumentResolver 와 같은
 * 스프링 환결설정을 하는 클래스
 */
@Configuration
public class AppServerConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
