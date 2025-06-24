package com.tech.engg5.springbatch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobInfo {

  @JacksonXmlProperty(localName = "jobTitle")
  String jobTitle;

  @JacksonXmlProperty(localName = "jobFamily")
  String jobFamily;

  @JacksonXmlProperty(localName = "salary")
  double salary;

  @JacksonXmlProperty(localName = "jobLevel")
  int jobLevel;

  @JacksonXmlProperty(localName = "jobStartDate")
  String jobStartDate;
}
