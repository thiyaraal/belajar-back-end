package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BearerTokenInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    String path = req.getRequestURI();
    if (path.startsWith("/auth/login") || path.startsWith("/auth/status")) return true;

    String header = req.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer ")) {
      res.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing token");
      return false;
    }
    String token = header.substring(7);
    try {
      String tokenRoomId = JwtUtil.validateTokenAndGetRoomId(token);
      req.setAttribute("tokenRoomId", tokenRoomId);
      return true;
    } catch (RuntimeException ex) {
      res.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired token");
      return false;
    }
  }
}