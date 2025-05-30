package com.tech.engg5.datamapper.model.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "data-file")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataFile {

  String path;
}
