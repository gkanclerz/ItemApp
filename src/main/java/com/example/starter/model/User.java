package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {
  @JsonProperty("_id")
  private UUID id;
  @JsonProperty("username")
  private String username;
  @JsonProperty("password")
  private String password;
}
