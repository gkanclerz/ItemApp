package com.example.starter.handler;

import com.example.starter.service.JwtAuthService;
import io.vertx.ext.auth.jwt.JWTAuth;

public class JwtAuthHandler {

  private final JwtAuthService jwtAuthService;

  public JwtAuthHandler(JwtAuthService jwtAuthService) {
    this.jwtAuthService = jwtAuthService;
  }

  public JWTAuth createJwtAuth(){
    return jwtAuthService.createJwtAuth();
  }
}
