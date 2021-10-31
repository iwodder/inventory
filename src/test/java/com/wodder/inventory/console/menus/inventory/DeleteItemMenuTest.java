package com.wodder.inventory.console.menus.inventory;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DeleteItemMenuTest extends BaseMenuTest {

	@Test
	@DisplayName("Delete Item Menu prints menu")
	void delete_item_menu_print() {
		DeleteItemMenu deleteItemMenu = new DeleteItemMenu();
		deleteItemMenu.printMenu(out);
		String expected = String.format("====== Delete Item ======%nEnter item id or name to delete, e.g. 1 or \"bread\"%n");
		assertEquals(expected, baosOut.toString());
	}
}
