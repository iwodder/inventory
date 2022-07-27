package com.wodder.console.menus.inventoryitems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateItemMenuTest extends BaseMenuTest {

  @Test
  @DisplayName("Can correctly display menu text for Update Item Menu")
  void prints_menu() {
    UpdateItemMenu menu = new UpdateItemMenu(null);
    menu.printMenu(out);
    String expected =
        String.format(
            "====== Update Item ======%nEnter id of item to update with new value.%ne.g. id=1 name=<new name>%n");
    assertEquals(expected, baosOut.toString());
  }
}
