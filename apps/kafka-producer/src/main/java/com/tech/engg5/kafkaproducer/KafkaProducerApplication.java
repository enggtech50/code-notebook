package com.tech.engg5.kafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaProducerApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(KafkaProducerApplication.class);
    application.run(args);
  }
}
