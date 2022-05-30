package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OnHandTest {

  @ParameterizedTest
  @ValueSource(doubles = {-0.1, -0.0, -1})
  @DisplayName("Should not allow negative values for unit quantity")
  void nonNegativeUnitQty(double value) {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
        () -> OnHand.of(value, 1, 1, 1));
    assertEquals("Unit quantity can't be negative", e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {-0.1, -0.0, -1})
  @DisplayName("Should not allow negative values for case quantity")
  void nonNegativeCaseQty(double value) {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
        () -> OnHand.of(0, value, 1, 1));
    assertEquals("Case quantity can't be negative", e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {-0.1, -0.0, -1})
  @DisplayName("Should not allow negative values for unit price")
  void nonNegativeUnitPrice(double value) {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
        () -> OnHand.of(0, 0, value, 1));
    assertEquals("Unit price can't be negative", e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {-0.1, -0.0, -1})
  @DisplayName("Should not allow negative values for case price")
  void nonNegativeCasePrice(double value) {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
        () -> OnHand.of(0, 0, 0, value));
    assertEquals("Case price can't be negative", e.getMessage());
  }
}
