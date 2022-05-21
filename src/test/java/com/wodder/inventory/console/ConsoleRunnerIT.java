package com.wodder.inventory.console;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.console.menus.MainMenu;
import com.wodder.inventory.console.menus.inventoryitems.BaseMenuTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
class ConsoleRunnerIT extends BaseMenuTest {

  @Test
  @DisplayName("Main menu can exit the program loop using exit menu")
  void start() {
    setChars("1\n");
    MainMenu mainMenu = new MainMenu(null);
    mainMenu.addMenu(new ExitMenu());
    ConsoleRunner runner = new ConsoleRunner(inputStream, out, err);
    runner.setMenu(mainMenu);
    runner.start();
    assertTrue(true, "Error occurred trying to exit.");
  }
}
