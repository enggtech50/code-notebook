package com.tech.engg5.vertxmongo.controller;

import com.tech.engg5.vertxmongo.model.Product;
import com.tech.engg5.vertxmongo.repository.ProductRepository;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(VertxExtension.class)
public class ProductControllerTest {

  private ProductRepository repository;
  private WebClient webClient;

  @BeforeEach
  void setUp(Vertx vertx, VertxTestContext testContext) {
    repository = mock(ProductRepository.class);
    Router router = Router.router(vertx);
    new ProductController(router, repository);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888)
      .onSuccess(server -> {
        webClient = WebClient.create(vertx, new WebClientOptions().setDefaultPort(8888));
        testContext.completeNow();
      });
  }

  @Test
  @DisplayName("Test getAll route")
  void testGetAll(VertxTestContext testContext) {
    when(repository.findAll()).thenReturn(Future.succeededFuture(
      List.of(new Product("1", "Product 1", 100.0))
    ));

    webClient.get("/products").send()
      .onComplete(testContext.succeeding(response -> testContext.verify(() -> {
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.bodyAsJsonArray().size()).isEqualTo(1);
        verify(repository).findAll();
        testContext.completeNow();
      })));
  }

  @Test
  @DisplayName("Test getById route")
  void testGetById(VertxTestContext testContext) {
    when(repository.findById("1")).thenReturn(Future.succeededFuture(
      new Product("1", "Product 1", 100.0)
    ));

    webClient.get("/products/1").send()
      .onComplete(testContext.succeeding(response -> testContext.verify(() -> {
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.bodyAsJsonObject().getString("name")).isEqualTo("Product 1");
        verify(repository).findById("1");
        testContext.completeNow();
      })));
  }

  @Test
  @DisplayName("Test create route")
  void testCreate(VertxTestContext testContext) {
    Product product = new Product("1", "Product 1", 100.0);
    when(repository.create(any())).thenReturn(Future.succeededFuture("1"));

    webClient.post("/products")
      .sendJson(product)
      .onComplete(testContext.succeeding(response -> testContext.verify(() -> {
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.bodyAsString()).isEqualTo("Created with ID: 1");
        verify(repository).create(any());
        testContext.completeNow();
      })));
  }

  @Test
  @DisplayName("Test update route")
  void testUpdate(VertxTestContext testContext) {
    Product product = new Product("1", "Updated Product", 150.0);
    when(repository.update(eq("1"), any())).thenReturn(Future.succeededFuture());

    webClient.put("/products/1")
      .sendJson(product)
      .onComplete(testContext.succeeding(response -> testContext.verify(() -> {
        assertThat(response.statusCode()).isEqualTo(204);
        verify(repository).update(eq("1"), any());
        testContext.completeNow();
      })));
  }

  @Test
  @DisplayName("Test delete route")
  void testDelete(VertxTestContext testContext) {
    when(repository.delete("1")).thenReturn(Future.succeededFuture());

    webClient.delete("/products/1").send()
      .onComplete(testContext.succeeding(response -> testContext.verify(() -> {
        assertThat(response.statusCode()).isEqualTo(204);
        verify(repository).delete("1");
        testContext.completeNow();
      })));
  }
}
