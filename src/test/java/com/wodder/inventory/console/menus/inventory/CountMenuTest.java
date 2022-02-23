package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.menus.inventory.handlers.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountMenuTest extends BaseMenuTest {

	@Mock
	CountHandler countHandler;

	@Test
	void printMenu() {
		CountMenu menu = new CountMenu(countHandler);
		menu.printMenu(out);
		String expected = String.format("====== InventoryCount Inventory ======%nChoose a location to count, enter on-hand values.%nEnter \"exit\" to leave menu.%n");
		assertEquals(expected, baosOut.toString());
	}
}
