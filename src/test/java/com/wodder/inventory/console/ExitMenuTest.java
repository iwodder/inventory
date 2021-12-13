package com.wodder.inventory.console;

import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
		exitMenu.process(null, null,null);
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
