package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryCountTest {

  @Test
  @DisplayName("Should be able to create a zero count")
  void zero() {
   InventoryCount result = InventoryCount.ofZero();
   assertEquals(0, result.getCases());
   assertEquals(0, result.getUnits());
  }

  @Test
  @DisplayName("Should be equal based on values")
  void equals() {
    InventoryCount count1 = InventoryCount.countFrom("1", "2.0");
    InventoryCount count2 = InventoryCount.countFrom("1", "2.0");

    assertEquals(count1, count2);
  }
}
