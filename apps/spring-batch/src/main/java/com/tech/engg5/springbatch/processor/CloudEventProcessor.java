package com.tech.engg5.springbatch.processor;

import com.tech.engg5.springbatch.constants.CloudEventConstant;
import com.tech.engg5.springbatch.model.EmployeeRaw;
import com.tech.engg5.springbatch.model.domain.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.Instant;

@Slf4j
public class CloudEventProcessor implements ItemProcessor<EmployeeRaw, CloudEvent> {

  @Override
  public CloudEvent process(EmployeeRaw record) throws Exception {
    LOG.info("Deserialized Employee Record - [{}]", record);

    return CloudEvent.builder()
      .source(CloudEventConstant.SOURCE_FILE)
      .contentType(CloudEventConstant.APPLICATION_JSON)
      .createdTs(Instant.now())
      .payload(record)
      .build();
  }
}
