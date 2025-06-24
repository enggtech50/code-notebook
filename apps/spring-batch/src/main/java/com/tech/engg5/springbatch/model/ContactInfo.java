package com.tech.engg5.springbatch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactInfo {

  @JacksonXmlProperty(localName = "email")
  String email;

  @JacksonXmlProperty(localName = "phoneNumber")
  String phoneNumber;
}
