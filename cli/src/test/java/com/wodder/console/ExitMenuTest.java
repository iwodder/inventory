package com.wodder.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.console.handlers.ExitHandler;
import com.wodder.console.menus.inventoryitems.BaseMenuTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExitMenuTest extends BaseMenuTest {
  private boolean ran = false;
  private ExitMenu exitMenu;

  @BeforeEach
  public void setup() {
    super.setup();
    exitMenu = new ExitMenu(new ExitHandler());
  }

  @Test
  @DisplayName("Exit menu prints Exiting...")
  void printMenu() {
    exitMenu.printMenu(out);
    assertEquals("Exiting...", baosOut.toString());
  }

  @Test
  @DisplayName("Exit menu invokes parent menu exit function")
  void handleInput() {
    ConsoleMenu testMenu = new TestMenu();
    testMenu.addMenu(exitMenu);
    exitMenu.process(null, null, null);
    assertTrue(ran);
  }

  class TestMenu extends RootMenu {

    public TestMenu() {
      super("Test", null);
    }

    @Override
    public void exitMenu() {
      ran = true;
    }
  }
}
