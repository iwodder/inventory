package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CountTest {

  @Test
  @DisplayName("Should be able to create a zero count")
  void zero() {
   Count result = Count.ofZero();
   assertEquals(0, result.getCases());
   assertEquals(0, result.getUnits());
  }

  @Test
  @DisplayName("Should be equal based on values")
  void equals() {
    Count count1 = Count.countFrom("1", "2.0");
    Count count2 = Count.countFrom("1", "2.0");

    assertEquals(count1, count2);
  }
}
