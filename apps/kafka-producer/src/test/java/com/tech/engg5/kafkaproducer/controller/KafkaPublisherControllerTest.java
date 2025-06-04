package com.tech.engg5.kafkaproducer.controller;

import com.tech.engg5.kafkaproducer.service.KafkaPublisherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@WebFluxTest(KafkaPublisherController.class)
public class KafkaPublisherControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private KafkaPublisherService kafkaPublisherService;

  @Test
  @DisplayName("Verify that a message is published successfully")
  void shouldPublishMessageSuccessfully() {

    String request = "Test Kafka Message";
    String key = "test-key";

    webTestClient.post()
      .uri(uriBuilder -> uriBuilder.path("/kafka/publisher/publish-message").queryParam("key", key).build())
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(request)
      .exchange()
      .expectStatus().isOk()
      .expectBody(String.class).isEqualTo("Message published successfully");

    verify(kafkaPublisherService).publishMessage(request, key);
  }

  @Test
  void shouldReturnErrorWhenPublishingFails() {

    String request = "Invalid message";
    String key = "fail-key";

    doThrow(new RuntimeException("Kafka error")).when(kafkaPublisherService).publishMessage(request, key);

    webTestClient.post()
      .uri(uriBuilder -> uriBuilder.path("/kafka/publisher/publish-message").queryParam("key", key).build())
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(request)
      .exchange()
      .expectStatus().is5xxServerError()
      .expectBody(String.class).isEqualTo("Failed to publish message: Kafka error");

    verify(kafkaPublisherService).publishMessage(request, key);
  }
}
