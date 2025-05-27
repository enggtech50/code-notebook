package com.tech.engg5.vertxmongo.repository;

import com.tech.engg5.vertxmongo.model.Product;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRepository {

  MongoClient mongoClient;
  String collectionName;

  public Future<String> create(Product product) {
    LOG.info("Creating product with name - [{}]", product.getName());
    return mongoClient.insert(collectionName, product.toJson());
  }

  public Future<List<Product>> findAll() {
    LOG.info("Returning all products.");
    return mongoClient.findWithOptions(collectionName, new JsonObject(), new FindOptions())
      .map(list -> list.stream().map(Product::fromJson).collect(Collectors.toList()));
  }

  public Future<Product> findById(String id) {
    LOG.info("Finding product by id: [{}]", id);
    return mongoClient.findOne(collectionName, new JsonObject().put("_id", id), null)
      .map(result -> result != null ? Product.fromJson(result) : null);
  }

  public Future<Void> update(String id, Product product) {
    LOG.info("Updating product with id: [{}]", id);
    return mongoClient.replaceDocuments(collectionName, new JsonObject().put("_id", id), product.toJson())
      .mapEmpty();
  }

  public Future<Void> delete(String id) {
    LOG.info("Deleting product with id: [{}]", id);
    return mongoClient.removeDocument(collectionName, new JsonObject().put("_id", id)).mapEmpty();
  }
}
