package com.wodder.inventory.console;

import com.wodder.inventory.console.menus.inventory.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ExitMenuTest extends BaseMenuTest {
	private boolean ran = false;

	@Test
	@DisplayName("Exit menu prints Exiting...")
	void printMenu() {
		ExitMenu exitMenu = new ExitMenu();
		exitMenu.printMenu(out);
		assertEquals("Exiting...", baosOut.toString());
	}

	@Test
	@DisplayName("Exit menu invokes parent menu exit function")
	void handleInput() {
		ConsoleMenu testMenu = new TestMenu();
		ExitMenu exitMenu = new ExitMenu();
		testMenu.addMenu(exitMenu);
		exitMenu.handleInput(null, null,null);
		assertTrue(ran);
	}

	class TestMenu extends RootMenu {

		public TestMenu() {
			super("Test");
		}

		@Override
		public void exitMenu() {
			ran = true;
		}
	}
}
