package com.example.starter.repository;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class ItemRepository {

  private final MongoClient mongoClient;

  public ItemRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }
  public Future<String> save(JsonObject item) {
    return mongoClient.save("Item", item);
  }

  public Future<List<JsonObject>> findAllByOwner(String owner) {
    JsonObject query = new JsonObject().put("owner",owner);
    return mongoClient.find("Item",query);
  }
}
