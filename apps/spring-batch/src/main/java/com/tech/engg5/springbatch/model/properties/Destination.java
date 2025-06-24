package com.tech.engg5.springbatch.model.properties;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Destination {

  String outputPath;
}
