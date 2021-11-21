package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewInventoryItemsMenuTest extends BaseMenuTest {

	@Test
	@DisplayName("Prints out the View Inventory Item Menu")
	void printsMenu() {
		SubMenu menu = new ViewInventoryItemsMenu();
		menu.printMenu(out);
		String expected = String.format("====== View Inventory Item(s) ======%nEnter id of item to view or \"all\" to see all items.%n");
		assertEquals(expected, baosOut.toString());
	}
}
