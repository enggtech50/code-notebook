package com.tech.engg5.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class SpringBatchApplication {

  public static void main(String[] args) {
    ReactorDebugAgent.init();
    SpringApplication application = new SpringApplication(SpringBatchApplication.class);
    application.run(args);
  }
}
