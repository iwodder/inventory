package com.wodder.inventory.console.menus;

import com.wodder.inventory.application.implementations.*;
import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuFactoryTest {

	@Test
	@DisplayName("Creates a new CLI menu")
	void create_menu() {
		MenuFactory menus = new MenuFactory(new ServiceFactoryImpl(new PersistenceFactoryImpl()));
		ConsoleMenu mainMenu = menus.createMainMenu();
		assertNotNull(mainMenu);
		assertEquals("Main Menu", mainMenu.getMenuName());
		assertInstanceOf(RootMenu.class, mainMenu);
	}

	@Test
	@DisplayName("Main Menu contains Inventory Item Menu")
	void main_menu_contains_inventory_menu() {
		MenuFactory menus = new MenuFactory(new ServiceFactoryImpl());
		ConsoleMenu menu = menus.createMainMenu();
		assertTrue(menu.containsMenu(InventoryItemMenu.class));
	}
}
