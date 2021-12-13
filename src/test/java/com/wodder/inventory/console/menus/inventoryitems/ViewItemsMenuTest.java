package com.wodder.inventory.console.menus.inventoryitems;

import com.wodder.inventory.console.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewItemsMenuTest extends BaseMenuTest {

	@Test
	@DisplayName("Prints out the View Inventory Item Menu")
	void printsMenu() {
		SubMenu menu = new ViewItemsMenu(null);
		menu.printMenu(out);
		String expected = String.format("====== View Inventory Item(s) ======%nEnter id of item to view or \"all\" to see all items.%n");
		assertEquals(expected, baosOut.toString());
	}
}
