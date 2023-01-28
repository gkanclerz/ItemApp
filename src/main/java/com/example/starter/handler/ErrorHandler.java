package com.example.starter.handler;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.validation.BadRequestException;

public class ErrorHandler {

  public static void buildHandler(Router router) {
    router.errorHandler(400, rc -> {
      if (rc.failure() instanceof BadRequestException) {
          rc.response().setStatusCode(400).setStatusMessage("Bad request").end();
        }
      });
  }
}
