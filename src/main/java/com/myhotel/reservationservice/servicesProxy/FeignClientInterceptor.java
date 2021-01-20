package com.myhotel.reservationservice.servicesProxy;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AUTHORIZATION_HEADER);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader());

    }
}
