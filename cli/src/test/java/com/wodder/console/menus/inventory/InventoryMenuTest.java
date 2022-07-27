package com.wodder.console.menus.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.console.menus.inventoryitems.BaseMenuTest;
import org.junit.jupiter.api.Test;

class InventoryMenuTest extends BaseMenuTest {

  @Test
  void prints_title() {
    InventoryMenu menu = new InventoryMenu();
    menu.printMenu(out);
    assertEquals(
        String.format("====== Inventory Menu ======%nPlease choose a menu entry.%n"),
        baosOut.toString());
  }
}
