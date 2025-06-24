package com.tech.engg5.springbatch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRaw {

  @JacksonXmlProperty(localName = "employeeId")
  String employeeId;

  @JacksonXmlProperty(localName = "employeeName")
  String employeeName;

  @JacksonXmlProperty(localName = "age")
  int age;

  @JacksonXmlProperty(localName = "gender")
  String gender;

  @JacksonXmlProperty(localName = "contactInfo")
  ContactInfo contactInfo;

  @JacksonXmlProperty(localName = "addressInfo")
  AddressInfo addressInfo;

  @JacksonXmlProperty(localName = "jobInfo")
  JobInfo jobInfo;
}
