package com.devpet.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
@Component
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private static final String AUTHORIZATION_VALUE = "12345";

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //Http Servlet의 요청과 응답, 상태 및 기타 zuul의 정보를 담고 있다.
        //RequestContext 객체로부터 HttpServletRequest를 가져와 로깅에 필요한 Http Body와 요청 Uri, 그리고 인증에 필요한 Http Header 정보를 추출할 수 있다.
        log.info("===== START Pre Filter. =====");

        /** 로깅 구현
         * 요청 URI는 String reqUri = ctx.getRequest(). getRequestURI(); 로 간단히 가져올 수 있습니다.
         * 요청 Http Body 정보는 getReqHttpBody(RequestContext ctx)  메서드로 구현하였습니다.
         * RequestContext 객체에서 InputStream으로 추출하여 StreamUtils 유틸로 String으로 변환하여 반환합니다.
         */
        String reqUri = ctx.getRequest().getRequestURI();
        String reqHttpBody = getReqHttpBody(ctx);
        log.info("[Pre Filter] : Request reqUri : {} HttpBody : {}", reqUri, reqHttpBody);

        String authorization = ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isEmpty(authorization) || !AUTHORIZATION_VALUE.equals(authorization)) {
            respError(ctx);
        }

        ctx.addZuulRequestHeader("foo", "bar");
        return null;

    }

    private void respError(RequestContext ctx) {
        try {
            ctx.setSendZuulResponse(false);
            ctx.getResponse().sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getReqHttpBody(RequestContext ctx) {
        String reqHttpBody = null;
        try {
            InputStream in = (InputStream) ctx.get("requestEntity");
            if (in == null) {
                in = ctx.getRequest().getInputStream();
                reqHttpBody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            log.error("It is failed to obtaining Request Http Body.", e);
            return "";
        }
        return reqHttpBody;
    }
}
