package com.tech.engg5.kafkaproducer.controller;

import com.tech.engg5.kafkaproducer.service.KafkaPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafka/publisher")
public class KafkaPublisherController {

  private final KafkaPublisherService kafkaPublisherService;

  public KafkaPublisherController(KafkaPublisherService kafkaPublisherService) {
    this.kafkaPublisherService = kafkaPublisherService;
  }

  @PostMapping("/publish-message")
  public ResponseEntity<String> publishMessage(@RequestBody String request, @RequestParam String key) {
    LOG.info("Received request to publish message with key: {}", key);
    try {
      kafkaPublisherService.publishMessage(request, key);
      return ResponseEntity.ok("Message published successfully");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to publish message: " + e.getMessage());
    }
  }
}
