package com.wodder.inventory.console;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SubMenuTest {

	@Test
	@DisplayName("Submenu should always return false")
	void exitMenu() {
		ConsoleMenu menu = new SubMenu("Sub Menu");
		assertFalse(menu.exitMenu());
	}
}
