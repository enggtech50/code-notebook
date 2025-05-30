package com.tech.engg5.datamapper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.engg5.datamapper.model.Product;
import com.tech.engg5.datamapper.model.properties.DataFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonDataService {

  final DataFile dataFile;
  final ObjectMapper objectMapper = new ObjectMapper();

  public void marshalJsonObject() {
    try {
      var product = Product.builder()
        .productId("PRD001")
        .productName("Book")
        .productPrice(17.99)
        .build();

      LOG.info("Marshalling java object: [{}]", product.toString());

      File file = new File(dataFile.getPath(), "product-output.json");
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, product);

    }catch (Exception exc) {
      LOG.error("Error while marshalling JSON object.");
      exc.printStackTrace();
    }
  }

  public void unmarshallJsonObject() {
    try {
      File file = new File(dataFile.getPath(), "product-output.json");
      if (!file.exists()) {
        LOG.warn("File does not exist: {}", file.getAbsolutePath());
        return;
      }

      Product product = objectMapper.readValue(file, Product.class);
      LOG.info("Unmarshalled JSON to Product object: {}", product.toString());

    } catch (Exception exc) {
      LOG.error("Error while unmarshalling JSON object.");
      exc.printStackTrace();
    }
  }
}
