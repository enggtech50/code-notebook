package com.tech.engg5.vertxmongo.repository;

import com.tech.engg5.vertxmongo.model.Product;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(VertxExtension.class)
public class ProductRepositoryTest {

  private MongoClient mongoClient;
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    mongoClient = mock(MongoClient.class);
    productRepository = new ProductRepository(mongoClient, "vertx-mongo-products");
  }

  @Test
  @DisplayName("Verify that a product is created successfully.")
  void testCreate(VertxTestContext testContext) {
    Product product = new Product("1", "Test Product", 100.0);
    when(mongoClient.insert(any(), any())).thenReturn(Future.succeededFuture("1"));

    productRepository.create(product).onComplete(testContext.succeeding(id -> {
      assertThat(id).isEqualTo("1");
      verify(mongoClient).insert(eq("vertx-mongo-products"), eq(product.toJson()));
      testContext.completeNow();
    }));
  }

  @Test
  @DisplayName("Verify that all products are retrieved successfully.")
  void testFindAll(VertxTestContext testContext) {
    JsonObject productJson = new Product("1", "Test Product", 100.0).toJson();
    when(mongoClient.findWithOptions(any(), any(), any())).thenReturn(Future.succeededFuture(List.of(productJson)));

    productRepository.findAll().onComplete(testContext.succeeding(products -> {
      assertThat(products).hasSize(1);
      assertThat(products.get(0).getName()).isEqualTo("Test Product");
      verify(mongoClient).findWithOptions(eq("vertx-mongo-products"), any(), any());
      testContext.completeNow();
    }));
  }

  @Test
  @DisplayName("Verify that a product is found by ID successfully.")
  void testFindById(VertxTestContext testContext) {
    JsonObject productJson = new Product("1", "Test Product", 100.0).toJson();
    when(mongoClient.findOne(any(), any(), any())).thenReturn(Future.succeededFuture(productJson));

    productRepository.findById("1").onComplete(testContext.succeeding(product -> {
      assertThat(product).isNotNull();
      assertThat(product.getName()).isEqualTo("Test Product");
      verify(mongoClient).findOne(eq("vertx-mongo-products"), eq(new JsonObject().put("_id", "1")), isNull());
      testContext.completeNow();
    }));
  }

  @Test
  @DisplayName("Verify that a product is updated successfully.")
  void testUpdate(VertxTestContext testContext) {
    Product product = new Product("1", "Updated Product", 150.0);
    when(mongoClient.replaceDocuments(any(), any(), any())).thenReturn(Future.succeededFuture());

    productRepository.update("1", product).onComplete(testContext.succeeding(v -> {
      verify(mongoClient).replaceDocuments(eq("vertx-mongo-products"), eq(new JsonObject().put("_id", "1")), eq(product.toJson()));
      testContext.completeNow();
    }));
  }

  @Test
  @DisplayName("Verify that a product is deleted successfully.")
  void testDelete(VertxTestContext testContext) {
    when(mongoClient.removeDocument(any(), any())).thenReturn(Future.succeededFuture());

    productRepository.delete("1").onComplete(testContext.succeeding(v -> {
      verify(mongoClient).removeDocument(eq("vertx-mongo-products"), eq(new JsonObject().put("_id", "1")));
      testContext.completeNow();
    }));
  }
}
