package com.tech.engg5.datamapper.controller;

import com.tech.engg5.datamapper.service.XmlDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlDataController {

  @Autowired
  XmlDataService xmlDataService;

  @GetMapping("/marshal-xml")
  public void marshalXmlData() {
    xmlDataService.marshalXMLObject();
  }

  @GetMapping("/unmarshal-xml")
  public void unmarshalXmlData() {
    xmlDataService.unmarshalXmlObject();
  }
}
