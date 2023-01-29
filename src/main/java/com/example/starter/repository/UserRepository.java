package com.example.starter.repository;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class UserRepository {

  private final MongoClient mongoClient;

  public UserRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public Future<String> save(JsonObject jsonObject){
    return mongoClient.save("User", jsonObject);
  }
  public Future<JsonObject> getByUserName(String username) {
    JsonObject query = new JsonObject().put("username", username);
    return mongoClient.findOne("User", query, null);
  }
}
