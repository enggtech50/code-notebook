package com.tech.engg5.kafkaproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaPublisherService {

  @Value("${spring.kafka.producer.topic}")
  private String kafkaTopicName;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaPublisherService(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishMessage(String message, String key) {
    LOG.info("Inside KafkaPublisherService.publishMessage method. Publishing message - [{}]", message);
    try {
      kafkaTemplate.send(kafkaTopicName, key, message);
      LOG.info("Message published successfully to kafka-topic - [{}]", kafkaTopicName);
    } catch (Exception exc) {
      LOG.error("Error publishing message to kafka-topic - [{}]. Error: {}", kafkaTopicName, exc.getMessage(), exc);
      throw new RuntimeException("Failed to publish message to Kafka topic", exc);
    }
  }
}
