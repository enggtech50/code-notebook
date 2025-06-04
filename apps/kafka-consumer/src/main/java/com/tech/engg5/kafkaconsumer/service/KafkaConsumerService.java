package com.tech.engg5.kafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

  @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
    try {
      LOG.info("Received message - [{}] from topic - [{}]. Metadata - [Partition: {}, Offset: {}, Key: {}]",
        record.value(), record.topic(), record.partition(), record.offset(), record.key());
      ack.acknowledge();
    } catch (Exception exc) {
      LOG.error("Error while consuming message.", exc);
    }
  }
}
