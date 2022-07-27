package com.wodder.console.menus.inventoryitems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.wodder.console.ConsoleMenu;
import com.wodder.console.ExitMenu;
import com.wodder.console.handlers.DefaultRootMenuHandler;
import com.wodder.console.handlers.ExitHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryItemMenuTest extends BaseMenuTest {

  @BeforeEach
  public void setup() {
    super.setup();
  }

  @Test
  @DisplayName("Inventory Item Menu prints header")
  void prints_name() {
    ConsoleMenu menu = new InventoryItemMenu();
    menu.printMenu(out);
    assertEquals(
        String.format("====== Inventory Item Menu ======%nPlease choose a menu entry.%n"),
        baosOut.toString());
  }

  @Test
  @DisplayName("Inventory Item Menu handles input")
  void handles_input() {
    setChars("1\n");
    ConsoleMenu menu = new InventoryItemMenu(new DefaultRootMenuHandler());
    ConsoleMenu menu1 = new ExitMenu(new ExitHandler());
    menu.addMenu(menu1);

    menu.process(in, out, err);
    assertSame(menu1, menu.getActiveMenu());
  }
}
