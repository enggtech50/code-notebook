package com.tech.engg5.springbatch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressInfo {

  @JacksonXmlProperty(localName = "street")
  String street;

  @JacksonXmlProperty(localName = "city")
  String city;

  @JacksonXmlProperty(localName = "state")
  String state;

  @JacksonXmlProperty(localName = "country")
  String country;

  @JacksonXmlProperty(localName = "zipCode")
  String zipCode;
}
