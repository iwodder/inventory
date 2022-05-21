package com.wodder.inventory.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

  @Test
  @DisplayName("Returns a meaningful string representation")
  void basic_to_string() {
    Price p = new Price(new BigDecimal("0.65"), new BigDecimal("90.11"));
    assertEquals("Item Price=$0.65, Case Price=$90.11", p.toString());
  }

  @Test
  @DisplayName("Returns a meaningful string representation when case price isn't provided")
  void to_string_no_case_price() {
    Price p = new Price(new BigDecimal("0.65"));
    assertEquals("Item Price=$0.65, Case Price=N/A", p.toString());
  }

  @Test
  @DisplayName("Two prices are equal based on values")
  void equal() {
    Price p = new Price(new BigDecimal("0.65"), new BigDecimal("90.11"));
    Price p1 = new Price(new BigDecimal("0.65"), new BigDecimal("90.11"));
    assertEquals(p, p1);
  }
}
