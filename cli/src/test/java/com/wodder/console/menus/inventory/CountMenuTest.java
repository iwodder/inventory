package com.wodder.console.menus.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.console.menus.inventory.handlers.CountHandler;
import com.wodder.console.menus.inventoryitems.BaseMenuTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountMenuTest extends BaseMenuTest {

  @Mock
  CountHandler countHandler;

  @Test
  void printMenu() {
    CountMenu menu = new CountMenu(countHandler);
    menu.printMenu(out);
    String expected =
        String.format(
            "====== InventoryCount Inventory ======%nChoose a location to count, enter on-hand values.%nEnter \"exit\" to leave menu.%n");
    assertEquals(expected, baosOut.toString());
  }
}
