package com.tech.engg5.vertxmongo.model;

import io.vertx.core.json.JsonObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

  String id;
  String name;
  double price;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  public JsonObject toJson() {
    JsonObject json = new JsonObject()
      .put("name", name)
      .put("price", price);
    if (id != null) json.put("_id", id);
    return json;
  }

  public static Product fromJson(JsonObject json) {
    return new Product(
      json.getString("_id"),
      json.getString("name"),
      json.getDouble("price")
    );
  }
}
