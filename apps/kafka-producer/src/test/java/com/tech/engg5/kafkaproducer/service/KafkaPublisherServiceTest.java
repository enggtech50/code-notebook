package com.tech.engg5.kafkaproducer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaPublisherServiceTest {

  @Mock
  private KafkaTemplate<String, String> kafkaTemplate;

  @InjectMocks
  private KafkaPublisherService kafkaPublisherService;

  @BeforeEach
  void setUp() {
    // Manually inject the topic value since @Value does not work in unit test
    ReflectionTestUtils.setField(kafkaPublisherService, "kafkaTopicName", "test-topic");
  }

  @Test
  @DisplayName("Verify that message is published to Kafka topic successfully")
  void shouldPublishMessageSuccessfully() {
    String key = "test-key";
    String message = "test-message";

    kafkaPublisherService.publishMessage(message, key);

    verify(kafkaTemplate, times(1)).send("test-topic", key, message);
  }

  @Test
  @DisplayName("Verify that exception is thrown when publishing fails")
  void shouldThrowExceptionWhenPublishingFails() {
    String key = "fail-key";
    String message = "fail-message";
    RuntimeException kafkaException = new RuntimeException("Kafka failure");

    doThrow(kafkaException).when(kafkaTemplate).send("test-topic", key, message);

    RuntimeException thrown = assertThrows(RuntimeException.class, () ->
      kafkaPublisherService.publishMessage(message, key));

    assertEquals("Failed to publish message to Kafka topic", thrown.getMessage());
    verify(kafkaTemplate, times(1)).send("test-topic", key, message);
  }
}
