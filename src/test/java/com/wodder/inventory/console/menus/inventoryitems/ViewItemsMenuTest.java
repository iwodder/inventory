package com.wodder.inventory.console.menus.inventoryitems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.console.SubMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ViewItemsMenuTest extends BaseMenuTest {

  @Test
  @DisplayName("Prints out the View Inventory Item Menu")
  void printsMenu() {
    SubMenu menu = new ViewItemsMenu(null);
    menu.printMenu(out);
    String expected =
        String.format(
            "====== View Inventory Item(s) ======%nEnter id of item to view or \"all\" to see all items.%n");
    assertEquals(expected, baosOut.toString());
  }
}
