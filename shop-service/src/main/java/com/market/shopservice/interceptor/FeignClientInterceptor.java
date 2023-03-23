package com.market.shopservice.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private final HttpServletRequest httpServletRequest;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    String token = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
    requestTemplate.header(AUTHORIZATION_HEADER, token);
  }
}
