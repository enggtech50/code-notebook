package com.tech.engg5.datamapper.controller;

import com.tech.engg5.datamapper.service.JsonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonDataController {

  @Autowired
  JsonDataService jsonDataService;

  @GetMapping("/marshal-json")
  public void marshalJsonData() {
    jsonDataService.marshalJsonObject();
  }

  @GetMapping("/unmarshal-json")
  public void unmarshalJsonData() {
    jsonDataService.unmarshallJsonObject();
  }
}
