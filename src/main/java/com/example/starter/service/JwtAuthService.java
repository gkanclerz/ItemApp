package com.example.starter.service;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;


public class JwtAuthService {

  private final Vertx vertx;

  public JwtAuthService(Vertx vertx) {
    this.vertx = vertx;
  }

  public String generateToken(JsonObject user){
    JWTAuth provider = JWTAuth.create(vertx, new JWTAuthOptions()
      .setKeyStore(new KeyStoreOptions()
        .setPassword("secret")));
    return provider.generateToken(new JsonObject().put("username", user.getString("username")));
  }

  public JWTAuth createJwtAuth(){
    return JWTAuth.create(vertx, new JWTAuthOptions()
      .setKeyStore(new KeyStoreOptions()
        .setPassword("secret")));
  }
}
