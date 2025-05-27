package com.tech.engg5.vertxmongo.controller;

import com.tech.engg5.vertxmongo.model.Product;
import com.tech.engg5.vertxmongo.repository.ProductRepository;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

  ProductRepository repository;

  public ProductController(Router router, ProductRepository repository) {
    this.repository = repository;
    setRoutes(router);
  }

  private void setRoutes(Router router) {
    router.get("/products").handler(this::getAll);
    router.get("/products/:id").handler(this::getById);
    router.post("/products").handler(this::create);
    router.put("/products/:id").handler(this::update);
    router.delete("/products/:id").handler(this::delete);
  }

  private void getAll(RoutingContext ctx) {
    repository.findAll().onSuccess(products -> {
      ctx.response()
        .putHeader("Content-Type", "application/json")
        .end(Json.encodePrettily(products));
    }).onFailure(err -> ctx.fail(500, err));
  }

  private void getById(RoutingContext ctx) {
    String id = ctx.pathParam("id");
    repository.findById(id).onSuccess(product -> {
      if (product == null) {
        ctx.response().setStatusCode(404).end();
      } else {
        ctx.response()
          .putHeader("Content-Type", "application/json")
          .end(Json.encodePrettily(product));
      }
    }).onFailure(err -> ctx.fail(500, err));
  }

  private void create(RoutingContext ctx) {
    ctx.request().body().onSuccess(buffer -> {
      Product product = Json.decodeValue(buffer, Product.class);
      repository.create(product).onSuccess(id -> {
        ctx.response().setStatusCode(201).end("Created with ID: " + id);
      }).onFailure(err -> ctx.fail(500, err));
    });
  }

  private void update(RoutingContext ctx) {
    String id = ctx.pathParam("id");
    ctx.request().body().onSuccess(buffer -> {
      Product product = Json.decodeValue(buffer, Product.class);
      repository.update(id, product).onSuccess(v -> {
        ctx.response().setStatusCode(204).end();
      }).onFailure(err -> ctx.fail(500, err));
    });
  }

  private void delete(RoutingContext ctx) {
    String id = ctx.pathParam("id");
    repository.delete(id).onSuccess(v -> {
      ctx.response().setStatusCode(204).end();
    }).onFailure(err -> ctx.fail(500, err));
  }
}
