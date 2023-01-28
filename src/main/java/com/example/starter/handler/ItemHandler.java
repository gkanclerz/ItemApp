package com.example.starter.handler;

import com.example.starter.service.ItemService;
import io.vertx.ext.web.RoutingContext;

public class ItemHandler {

  private final ItemService itemService;

  public ItemHandler(ItemService itemService) {
    this.itemService = itemService;
  }

  public void addItem(RoutingContext context) {
    String name = context.body().asJsonObject().getString("name");
    String username = context.user().principal().getString("username");
    itemService.addItem(username,name)
      .onSuccess(success -> {
        context.response().setStatusCode(204).setStatusMessage("Item created successfull.").end();
      })
      .onFailure(fail -> {
        context.response().setStatusCode(500).end();
        fail.printStackTrace();
      });
  }

  public void getItems(RoutingContext context) {
    String username = context.user().principal().getString("username");
    itemService.getItems(username)
      .onSuccess(success -> {
        context.response().setStatusCode(200)
          .end(success.toString());
      })
      .onFailure(fail -> {
        context.response().setStatusCode(500).end();
        fail.printStackTrace();
      });
  }
}
