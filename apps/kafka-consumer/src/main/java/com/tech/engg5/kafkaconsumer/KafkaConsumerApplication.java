package com.tech.engg5.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaConsumerApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(KafkaConsumerApplication.class);
    application.run(args);
  }
}
