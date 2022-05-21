package com.wodder.inventory.console;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubMenuTest {

  @Test
  @DisplayName("Submenu should always return false")
  void exitMenu() {
    ConsoleMenu menu = new SubMenu("Sub Menu");
    assertFalse(menu.getExit());
  }
}
