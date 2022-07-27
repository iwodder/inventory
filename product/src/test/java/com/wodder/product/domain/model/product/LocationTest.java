package com.wodder.product.domain.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationTest {

  @Test
  @DisplayName("Default location has unassigned value")
  void create() {
    Location l = new Location();
    assertEquals("unassigned", l.getName());
  }

  @Test
  @DisplayName("Location is assigned name at construction")
  void construction() {
    Location l = new Location("refrigerated");
    assertEquals("refrigerated", l.getName());
  }

  @Test
  @DisplayName("Trying to use empty string defaults to unassigned")
  void null_test() {
    Location l = new Location("");
    assertEquals("unassigned", l.getName());
  }

  @Test
  @DisplayName("Has copy constructor")
  void copy() {
    Location l = new Location("pantry");
    Location l2 = new Location(l);
    assertNotSame(l, l2);
    assertEquals(l, l2);
  }

  @Test
  @DisplayName("Locations are equal based on name")
  void name() {
    Location l = new Location();
    Location l2 = new Location();
    assertEquals(l, l2);
    assertNotSame(l, l2);
  }
}
