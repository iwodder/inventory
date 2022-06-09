package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InventoryCategoryTest {

  @ParameterizedTest
  @ValueSource(strings = {"Name", "Dairy", "Frozen"})
  @DisplayName("Category has a name")
  void name(String name) {
    InventoryCategory c = InventoryCategory.of(name);

    assertEquals(name, c.getName());
  }

  @ParameterizedTest
  @ValueSource(strings = {"Name", "Dairy", "Frozen"})
  @DisplayName("Category should be equal based on name and case insensitive")
  void equal(String name) {
    InventoryCategory c1 = InventoryCategory.of(name);
    InventoryCategory c2 = InventoryCategory.of(name.toUpperCase());

    assertEquals(c1, c2);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "0123"})
  @DisplayName("Category name must be non-numeric characters")
  void nonNumeric(String illegal) {
    assertThrows(IllegalArgumentException.class,
        () -> InventoryCategory.of(illegal));
  }

  @Test
  @DisplayName("Category name must be non-numeric characters")
  void nonNumeric() {
    assertThrows(IllegalArgumentException.class,
        () -> InventoryCategory.of(null));
  }
}
