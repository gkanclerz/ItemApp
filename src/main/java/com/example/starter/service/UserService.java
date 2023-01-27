package com.example.starter.service;

import com.example.starter.repository.UserRepository;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.HashingStrategy;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;

public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final JwtAuthService jwtAuthService;

  HashingStrategy hashingStrategy = HashingStrategy.load();

  public UserService(UserRepository userRepository, JwtAuthService jwtAuthService) {
    this.userRepository = userRepository;
    this.jwtAuthService = jwtAuthService;
  }


  public Future<JsonObject> registerUser(JsonObject user) {
    String passwordHash = hashingStrategy.hash("sha512", null, "salt", user.getString("password"));
    user.put("password",passwordHash);
    return userRepository.getByUserName(user.getString("username"))
      .onSuccess(success -> {
        if (success == null || success.isEmpty()) {
          userRepository.save(user).onSuccess(suc -> {
            logger.info("User id: " + user.getString("_id") + " saved");
          })
            .onFailure(fa -> {
              logger.info("Repository error");
            });
        } else {
          logger.info("The user with the given name already exists");
        }
      })
      .onFailure(fail -> logger.info("Server error"));
  }

  public Future<JsonObject> loginUser(JsonObject user){
    String username = user.getString("username");
    String password = user.getString("password");
    return userRepository.getByUserName(username)
      .onSuccess(success -> {
        if (success != null && hashingStrategy.verify(success.getString("password"),password)){
            success.put("token",jwtAuthService.generateToken(new JsonObject().put("username", username)));
        }
      })
      .onFailure(fail -> {
        logger.info("User not found");
      });
  }
}
