package com.tech.engg5.vertxmongo;

import com.tech.engg5.vertxmongo.config.MongoConfig;
import com.tech.engg5.vertxmongo.controller.ProductController;
import com.tech.engg5.vertxmongo.repository.ProductRepository;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    LOG.info("Starting Verticle...");

    ConfigStoreOptions fileStore = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject().put("path", "src/main/resources/application.yml"));

    ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

    retriever.getConfig(ar -> {
      if (ar.succeeded()) {
        LOG.info("Config file loaded successfully.");

        JsonObject config = ar.result();
        String collectionName = config.getJsonObject("db").getJsonObject("mongo").getString("collection");
        LOG.info("Collection name: [{}]", collectionName);

        MongoConfig.getMongoClient(vertx).future().onSuccess(mongoClient -> {
          LOG.info("MongoClient initialized successfully.");

          var productRepo = new ProductRepository(mongoClient, collectionName);
          Router router = Router.router(vertx);
          new ProductController(router, productRepo);

          vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888)
            .onSuccess(server -> {
              LOG.info("HTTP server started on port [{}]", server.actualPort());
              startPromise.complete();
            })
            .onFailure(startPromise::fail);
        }).onFailure(err -> {
          LOG.error("MongoClient initialization failed: {}", err.getMessage());
          startPromise.fail(err);
        });
      } else {
        LOG.error("Failed to load config: {}", ar.cause().getMessage());
        startPromise.fail(ar.cause());
      }
    });
  }
}

  /*@Override
  public void start(Promise<Void> startPromise) {
    ConfigStoreOptions fileStore = new ConfigStoreOptions()
      .setType("classpath")
      .setFormat("yaml")
      .setConfig(new JsonObject().put("path", "application.yml"));
    ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

    retriever.getConfig(ar -> {
      if (ar.succeeded()) {
        JsonObject config = ar.result();
        String collectionName = config.getJsonObject("db").getJsonObject("mongo").getString("collection");

        MongoConfig.getMongoClient(vertx).future().onSuccess(mongoClient -> {
          var productRepo = new ProductRepository(mongoClient, collectionName);
          Router router = Router.router(vertx);
          new ProductController(router, productRepo);

          vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888)
            .onSuccess(server -> {
              System.out.println("HTTP server started on port " + server.actualPort());
              startPromise.complete();
            })
            .onFailure(startPromise::fail);
        }).onFailure(startPromise::fail);
      } else {
        startPromise.fail(ar.cause());
      }
    });
  }*/
