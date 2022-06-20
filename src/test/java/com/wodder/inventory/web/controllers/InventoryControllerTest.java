package com.wodder.inventory.web.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.wodder.inventory.persistence.DataPopulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryControllerTest {

  DataPopulation p = new DataPopulation();

  @BeforeEach
  void setUp() {
    p.init();
  }

  @Test
  @Disabled
  @DisplayName("Groups active items by category")
  void init() {
    InventoryController c = new InventoryController();
    c.init();
    assertNotNull(c.getItemMap());
  }

  @Test
  void getInventoryDate() {}
}
