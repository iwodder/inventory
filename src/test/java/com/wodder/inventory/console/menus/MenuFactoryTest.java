package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuFactoryTest {

	@Test
	@DisplayName("Creates a new CLI menu")
	void create_menu() {
		MenuFactory menus = new MenuFactory();
		RootMenu mainMenu = menus.createMainMenu();
		assertNotNull(mainMenu);
		assertEquals("Main Menu", mainMenu.getMenuName());
	}
}
