package com.tech.engg5.datamapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataMapperApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(DataMapperApplication.class);
    application.run(args);
  }
}
