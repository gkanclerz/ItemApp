package com.example.starter.handler;

import com.example.starter.service.UserService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class UserHandler {

  private final UserService userService;

  public UserHandler(UserService userService) {
    this.userService = userService;
  }

  public void registerUser(RoutingContext context) {
    JsonObject user = context.body().asJsonObject();
    user.put("_id", UUID.randomUUID().toString());
    userService.registerUser(user)
      .onSuccess(success -> {
        if(success == null){
          context.response().setStatusCode(204).setStatusMessage("Registering successfull.").end();
        }
        context.response().setStatusCode(406).setStatusMessage("The user with the given name already exists").end();
      })
      .onFailure(fail -> {
        context.response().setStatusCode(500).end();
      });
  }

  public void loginUser(RoutingContext context) {
    JsonObject user = context.body().asJsonObject();
    userService.loginUser(user)
      .onSuccess(success -> {
          if(success != null){
            context.request().response().setStatusCode(200).end("token: " + success.getString("token"));
          }else {
            context.response().setStatusCode(406).setStatusMessage("Login or password wrong").end();
          }
      })
      .onFailure(fail -> context.response().setStatusCode(500).end());
  }
}
