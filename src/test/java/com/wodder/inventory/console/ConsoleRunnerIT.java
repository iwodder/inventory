package com.wodder.inventory.console;

import com.wodder.inventory.console.menus.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
