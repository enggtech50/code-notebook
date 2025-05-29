package com.tech.engg5.springbootmongo.config;

import com.tech.engg5.springbootmongo.model.Employee;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MongoConfig {

  @EventListener(ApplicationContextEvent.class)
  public void initIndicesAfterStartup(ApplicationContextEvent event) {
    val reactiveMongoTemplate = event.getApplicationContext().getBean(ReactiveMongoTemplate.class);

    val indexes = Stream.of(
      new AbstractMap.SimpleEntry<>(Employee.class, new Index(Employee.Fields.employeeId, Sort.Direction.ASC)
        .named(Employee.Fields.employeeId)));

    Mono.when(indexes
        .map(indexDef -> reactiveMongoTemplate.indexOps(indexDef.getKey()).ensureIndex(indexDef.getValue()))
        .collect(Collectors.toList()))
      .onErrorResume(throwable -> {
        LOG.error("An error occurred while creating indexes. Resuming application load.", throwable);
        return Mono.empty();
      })
      .doOnSubscribe(subscription -> LOG.info("Ensuring indexes are present"))
      .doOnTerminate(() -> LOG.info("Finished verifying indexes"))
      .doOnSuccess(unused -> LOG.info("Indexes verified successfully"))
      .block();
  }
}
