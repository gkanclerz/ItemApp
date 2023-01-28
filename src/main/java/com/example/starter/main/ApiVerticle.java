package com.example.starter.main;

import com.example.starter.handler.ItemHandler;
import com.example.starter.handler.JwtAuthHandler;
import com.example.starter.handler.UserHandler;
import com.example.starter.repository.ItemRepository;
import com.example.starter.repository.UserRepository;
import com.example.starter.router.ItemRouter;
import com.example.starter.router.UserRouter;
import com.example.starter.service.ItemService;
import com.example.starter.service.JwtAuthService;
import com.example.starter.service.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

public class ApiVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) throws Exception {

    MongoClient mongoClient = MongoClient.createShared(vertx,new JsonObject().put("db_name","ItemDb"));

    final JwtAuthService jwtAuthService = new JwtAuthService(vertx);
    final JwtAuthHandler jwtAuthHandler = new JwtAuthHandler(jwtAuthService);

    final UserRepository userRepository = new UserRepository(vertx,mongoClient);
    final UserService userService = new UserService(userRepository, jwtAuthService);
    final UserHandler userHandler = new UserHandler(userService);
    final UserRouter userRouter = new UserRouter(vertx,userHandler);

    final ItemRepository itemRepository = new ItemRepository(vertx,mongoClient);
    final ItemService itemService = new ItemService(itemRepository,userRepository);
    final ItemHandler itemHandler = new ItemHandler(itemService);
    final ItemRouter itemRouter = new ItemRouter(vertx,itemHandler, jwtAuthHandler);

    final Router router = Router.router(vertx);
    router.route().subRouter(itemRouter.buildItemRouter());
    router.route().subRouter(userRouter.buildUserRouter());

    vertx.createHttpServer().requestHandler(router).listen(3000, http -> {
      if (http.succeeded()) {
        promise.complete();
        System.out.println("HTTP server started on port 3000");
      } else {
        promise.fail(http.cause());
      }
    });
  }
}
