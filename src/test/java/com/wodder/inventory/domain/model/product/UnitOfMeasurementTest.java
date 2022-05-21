package com.wodder.inventory.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitOfMeasurementTest {

  @Test
  @DisplayName("Creating a unit of measurement requires a unit")
  void unit_required() {
    assertThrows(IllegalArgumentException.class, () -> new UnitOfMeasurement((String) null));
    assertThrows(IllegalArgumentException.class, () -> new UnitOfMeasurement(""));
  }
}
