package com.smilegate.devpet.appserver.config;

import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.service.JwtConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private JwtConverterService jwtConverterService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return jwtConverterService.getUserInfo(webRequest.getHeader("Authorization"));
    }
}
