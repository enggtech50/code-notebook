package com.tech.engg5.springbatch.model.properties;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Configuration("batch-processor")
public class Apps {

  Source source;
  Destination destination;
}
