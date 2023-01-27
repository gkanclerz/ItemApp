package com.example.starter.main;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise)  {
    vertx.deployVerticle(new ApiVerticle())
      .onSuccess(s -> System.out.println("Verticle " + ApiVerticle.class.getName() + " successfully deployed"))
      .onFailure(Throwable::printStackTrace);
  }
}
