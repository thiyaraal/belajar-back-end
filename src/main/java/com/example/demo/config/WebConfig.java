package com.example.demo.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.security.BearerTokenInterceptor;
import com.example.demo.security.TokenRoomIdResolver;

import lombok.RequiredArgsConstructor;
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final BearerTokenInterceptor bearerTokenInterceptor;
  private final TokenRoomIdResolver tokenRoomIdResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(bearerTokenInterceptor)
      .addPathPatterns("/**")
      .excludePathPatterns("/auth/login", "/auth/logout", "/auth/status/**");
  }

  @Override
  public void addArgumentResolvers(java.util.List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(tokenRoomIdResolver);
  }
}