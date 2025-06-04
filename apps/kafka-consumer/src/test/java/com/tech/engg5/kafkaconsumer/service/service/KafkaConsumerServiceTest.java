package com.tech.engg5.kafkaconsumer.service.service;

import com.tech.engg5.kafkaconsumer.service.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerServiceTest {

  @InjectMocks
  private KafkaConsumerService kafkaConsumerService;

  @Mock
  private ConsumerRecord<String, String> consumerRecord;

  @Mock
  private Acknowledgment acknowledgment;

  @Test
  @DisplayName("Verify that message is consumed and acknowledged successfully")
  void shouldConsumeMessageSuccessfully() {
    Mockito.when(consumerRecord.value()).thenReturn("test-value");
    Mockito.when(consumerRecord.topic()).thenReturn("test-topic");
    Mockito.when(consumerRecord.partition()).thenReturn(0);
    Mockito.when(consumerRecord.offset()).thenReturn(42L);
    Mockito.when(consumerRecord.key()).thenReturn("test-key");

    kafkaConsumerService.consumeMessage(consumerRecord, acknowledgment);

    Mockito.verify(acknowledgment, times(1)).acknowledge();
  }

  @Test
  @DisplayName("Verify that message consumption throws an exception when acknowledgment fails")
  void shouldHandleExceptionDuringConsumption() {

    Mockito.when(consumerRecord.value()).thenThrow(new RuntimeException("forced failure"));

    assertDoesNotThrow(() -> kafkaConsumerService.consumeMessage(consumerRecord, acknowledgment));

    Mockito.verify(acknowledgment, never()).acknowledge();
  }
}
