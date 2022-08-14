package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitOfMeasurementTest {

  @Test
  @DisplayName("Should be able to create an empty unit of measurement")
  void empty() {
    UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.empty();
    assertEquals("empty", unitOfMeasurement.getUnit());
  }

  @Test
  @DisplayName("Creating a unit of measurement requires a unit")
  void unit_required() {
    assertThrows(IllegalArgumentException.class, () -> new UnitOfMeasurement(""));
  }

  @Test
  @DisplayName("Should be equal based on values")
  void equals() {
    UnitOfMeasurement uom1 = new UnitOfMeasurement("Gallons");
    UnitOfMeasurement uom2 = new UnitOfMeasurement("gallons");

    assertEquals(uom1, uom2);
  }
}
