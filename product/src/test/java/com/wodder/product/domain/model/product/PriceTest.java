package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

  @Test
  @DisplayName("Returns a meaningful string representation")
  void basic_to_string() {
    Price p = new Price(new BigDecimal("0.65"));
    assertEquals("Price{value=0.65}", p.toString());
  }

  @Test
  @DisplayName("Two prices are equal based on values")
  void equal() {
    Price p = new Price(new BigDecimal("0.65"));
    Price p1 = new Price(new BigDecimal("0.65"));

    assertEquals(p, p1);
  }

  @Test
  @DisplayName("Should be able to create a zero price")
  void zero() {
    Price p = Price.ofZero();
    assertEquals(new BigDecimal("0.00"), p.getValue());
  }
}
