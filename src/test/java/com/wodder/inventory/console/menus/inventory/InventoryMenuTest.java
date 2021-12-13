package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryMenuTest extends BaseMenuTest {

	@Test
	void prints_title() {
		InventoryMenu menu = new InventoryMenu();
		menu.printMenu(out);
		assertEquals(String.format("====== Inventory Menu ======%nPlease choose a menu entry.%n"), baosOut.toString());
	}
}
