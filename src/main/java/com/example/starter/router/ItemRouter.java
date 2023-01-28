package com.example.starter.router;

import com.example.starter.handler.ItemHandler;
import com.example.starter.handler.ItemValidationHandler;
import com.example.starter.handler.JwtAuthHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

public class ItemRouter {

  private final Vertx vertx;
  private final ItemHandler itemHandler;
  private final JwtAuthHandler jwtAuthHandler;
  private final ItemValidationHandler itemValidationHandler;

  public ItemRouter(Vertx vertx, ItemHandler itemHandler, JwtAuthHandler jwtAuthHandler, ItemValidationHandler itemValidationHandler) {
    this.vertx = vertx;
    this.itemHandler = itemHandler;
    this.jwtAuthHandler = jwtAuthHandler;
    this.itemValidationHandler = itemValidationHandler;
  }

  public Router buildItemRouter() {
    final Router itemRouter = Router.router(vertx);

    itemRouter.route().handler(BodyHandler.create());
    itemRouter.post("/items").handler(JWTAuthHandler.create(jwtAuthHandler.createJwtAuth())).handler(itemValidationHandler.itemRequest()).handler(itemHandler::addItem);
    itemRouter.get("/items").handler(JWTAuthHandler.create(jwtAuthHandler.createJwtAuth())).handler(itemHandler::getItems);

    return itemRouter;
  }
}
