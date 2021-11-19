package com.wodder.inventory.console.menus.inventory;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UpdateItemMenuTest extends BaseMenuTest {

	@Test
	@DisplayName("Can correctly display menu text for Update Item Menu")
	void prints_menu() {
		UpdateItemMenu menu = new UpdateItemMenu();
		menu.printMenu(out);
		String expected = String.format("====== Update Item ======%nEnter id of item to update with new value.%ne.g. id=1 name=<new name>%n");
		assertEquals(expected, baosOut.toString());
	}
}
