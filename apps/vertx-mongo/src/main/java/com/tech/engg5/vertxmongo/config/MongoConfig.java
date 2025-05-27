package com.tech.engg5.vertxmongo.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoConfig {

  public static Promise<MongoClient> getMongoClient(Vertx vertx) {
    Promise<MongoClient> promise = Promise.promise();

    ConfigStoreOptions fileStore = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject().put("path", "src/main/resources/application.yml"));

    ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

    retriever.getConfig(ar -> {
      if (ar.succeeded()) {
        JsonObject config = ar.result();
        JsonObject mongoConfig = config.getJsonObject("db").getJsonObject("mongo");
        JsonObject mongoOptions = new JsonObject()
          .put("connection_string", mongoConfig.getString("connection_string"))
          .put("db_name", mongoConfig.getString("db_name"));

        MongoClient mongoClient = MongoClient.createShared(vertx, mongoOptions);
        promise.complete(mongoClient);
      } else {
        promise.fail(ar.cause());
      }
    });

    return promise;
  }
}
