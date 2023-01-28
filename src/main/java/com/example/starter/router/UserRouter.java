package com.example.starter.router;

import com.example.starter.handler.UserHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class UserRouter {

  private final Vertx vertx;
  private final UserHandler userHandler;

  public UserRouter(Vertx vertx, UserHandler userHandler) {
    this.vertx = vertx;
    this.userHandler = userHandler;
  }
  public Router buildUserRouter(){
    final Router userRouter = Router.router(vertx);

    userRouter.post("/register").handler(userHandler::registerUser);
    userRouter.post("/login").handler(userHandler::loginUser);

    return userRouter;
  }
}
