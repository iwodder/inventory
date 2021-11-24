package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
		assertEquals(String.format("====== Inventory Item Menu ======%nPlease choose a menu entry.%n"), baosOut.toString());
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
