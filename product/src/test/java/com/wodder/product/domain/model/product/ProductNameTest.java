package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {

  @Test
  @DisplayName("Should be able to create a product name")
  void create_name() {
    ProductName name = ProductName.of("Cheese");
    assertEquals("Cheese", name.getValue());
  }

  @Test
  @DisplayName("Should be equal based on value")
  void equal() {
    ProductName name1 = ProductName.of("Cheese");
    ProductName name2 = ProductName.of("Cheese");

    assertEquals(name1, name2, "ProductName should be equal based on value");
  }

  @Test
  @DisplayName("Should have a meaningful toString")
  void to_string() {
    ProductName n = ProductName.of("Cheese");
    assertEquals("ProductName{name='Cheese'}", n.toString());
  }
}
