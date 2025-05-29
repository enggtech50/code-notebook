package com.tech.engg5.springbootmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class SpringBootMongoApplication {

  public static void main(String[] args) {
    ReactorDebugAgent.init();
    SpringApplication application = new SpringApplication(SpringBootMongoApplication.class);
    application.run(args);
  }
}
