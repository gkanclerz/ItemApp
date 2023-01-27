package com.example.starter.model;

import java.util.UUID;

public class Item {
    private final UUID id = UUID.randomUUID();
    private UUID owner;
    private String name;

  public Item(UUID owner, String name) {
    this.owner = owner;
    this.name = name;
  }
}

