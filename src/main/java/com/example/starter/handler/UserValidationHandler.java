package com.example.starter.handler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.builder.Bodies;
import io.vertx.ext.web.validation.builder.ValidationHandlerBuilder;
import io.vertx.json.schema.SchemaParser;
import io.vertx.json.schema.SchemaRouter;
import io.vertx.json.schema.SchemaRouterOptions;
import io.vertx.json.schema.common.dsl.Keywords;
import io.vertx.json.schema.common.dsl.ObjectSchemaBuilder;

import static io.vertx.json.schema.common.dsl.Schemas.objectSchema;
import static io.vertx.json.schema.common.dsl.Schemas.stringSchema;

public class UserValidationHandler {

  private final Vertx vertx;

  public UserValidationHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  public ValidationHandler userRequest(){
    final SchemaParser schemaParser = buildSchemaParser();

    return ValidationHandlerBuilder
      .create(schemaParser)
      .body(Bodies.json(buildBodySchemaBuilder()))
      .build();
  }


  private SchemaParser buildSchemaParser() {
    return SchemaParser.createDraft7SchemaParser(SchemaRouter.create(vertx, new SchemaRouterOptions()));
  }

  private ObjectSchemaBuilder buildBodySchemaBuilder(){
    return objectSchema()
      .requiredProperty("username", stringSchema().with(Keywords.minLength(1)).with(Keywords.maxLength(50)))
      .requiredProperty("password", stringSchema().with(Keywords.minLength(4)).with(Keywords.maxLength(50)));
  }
}
