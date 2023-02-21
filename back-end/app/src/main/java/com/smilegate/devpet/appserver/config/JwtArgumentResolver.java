package com.smilegate.devpet.appserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilegate.devpet.appserver.model.UserInfo;
import io.netty.handler.codec.base64.Base64Decoder;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;
import java.util.Objects;

public class JwtArgumentResolver implements HandlerMethodArgumentResolver {
    ObjectMapper objectMapper = new ObjectMapper();
    /*
    * 파싱 가능하니? -> 지금 파라미터에 데이터를 넣어주려는 애가 우리가 파싱하려는 애가 맞니?
    * */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String tokenValue = Objects.requireNonNull(webRequest.getHeader(HttpHeaders.AUTHORIZATION)).replace("Bearer ","");
        String[] splitValue = tokenValue.split("\\.");
        String payload = splitValue[1];
        payload = new String(Base64.getDecoder().decode(payload));
        UserInfo info = objectMapper.readValue(payload,UserInfo.class);
        return info;
    }
}
