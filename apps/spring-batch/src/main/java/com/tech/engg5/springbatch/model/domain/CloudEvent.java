package com.tech.engg5.springbatch.model.domain;

import com.tech.engg5.springbatch.model.EmployeeRaw;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudEvent {

  String source;
  String contentType;
  Instant createdTs;
  EmployeeRaw payload;
}
