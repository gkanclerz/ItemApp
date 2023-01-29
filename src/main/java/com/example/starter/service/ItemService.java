package com.example.starter.service;

import com.example.starter.repository.ItemRepository;
import com.example.starter.repository.UserRepository;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.UUID;

public class ItemService {

  private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

  private final ItemRepository itemRepository;
  private final UserRepository userRepository;

  public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
    this.itemRepository = itemRepository;
    this.userRepository = userRepository;
  }

  public Future<JsonObject> addItem(String username, String name) {
    return userRepository.getByUserName(username)
      .onSuccess(success -> {
        JsonObject item = new JsonObject()
          .put("_id", UUID.randomUUID().toString())
          .put("owner", success.getString("_id"))
          .put("name", name);
        itemRepository.save(item).onSuccess(suc -> {
          logger.info("Item saved");
        })
          .onFailure(f -> {
            f.printStackTrace();
            logger.error("Repository error");
          });
      });

  }

  public Future<List<JsonObject>> getItems(String username) {
    return userRepository.getByUserName(username)
      .compose(suc -> itemRepository.findAllByOwner(suc.getString("_id"))
        .onSuccess(success -> {
          logger.info("User " + username + " get items");
        })
        .onFailure(throwable -> {
          throwable.printStackTrace();
          logger.error("Repository error");
        }));
  }
}
