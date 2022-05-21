package com.wodder.inventory.console.handlers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.console.ConsoleMenu;
import com.wodder.inventory.console.menus.inventoryitems.BaseMenuTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateItemHandlerTest extends BaseMenuTest {
  private boolean exited;

  @BeforeEach
  protected void setup() {
    super.setup();
    exited = false;
  }

  @Test
  @DisplayName("Entering exit calls exit on menu")
  void handles_exit() {
    setChars("exit");
    CreateItemHandler handler = new CreateItemHandler(null);
    handler.setMenu(new TestMenu());
    handler.handleInput(in, out, err);
    assertTrue(exited);
  }

  private class TestMenu extends ConsoleMenu {

    TestMenu() {
      super("Test Menu");
    }

    @Override
    public void exitMenu() {
      exited = true;
    }
  }
}
