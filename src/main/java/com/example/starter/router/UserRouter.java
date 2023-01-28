package com.example.starter.router;

import com.example.starter.handler.UserHandler;
import com.example.starter.handler.UserValidationHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class UserRouter {

  private final Vertx vertx;
  private final UserHandler userHandler;
  private final UserValidationHandler userValidationHandler;

  public UserRouter(Vertx vertx, UserHandler userHandler, UserValidationHandler userValidationHandler) {
    this.vertx = vertx;
    this.userHandler = userHandler;
    this.userValidationHandler = userValidationHandler;
  }
  public Router buildUserRouter(){
    final Router userRouter = Router.router(vertx);

    userRouter.post("/register").handler(userValidationHandler.userRequest()).handler(userHandler::registerUser);
    userRouter.post("/login").handler(userValidationHandler.userRequest()).handler(userHandler::loginUser);

    return userRouter;
  }
}
