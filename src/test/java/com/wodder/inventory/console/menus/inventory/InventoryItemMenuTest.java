package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventory.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemMenuTest {
	private ByteArrayOutputStream baos;
	private PrintStream ps;

	@BeforeEach
	void setup() {
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
	}

	@Test
	@DisplayName("Inventory Item Menu prints header")
	void prints_name() {
		ConsoleMenu menu = new InventoryItemMenu();
		menu.printMenu(ps);
		assertTrue(baos.toString().contains("Inventory Item Menu"));
	}

	@Test
	@DisplayName("Inventory Item Menu handles input")
	void handles_input() {
		Iterator<Character> itr = Arrays.asList('1', '\n').iterator();
		InputStream is = new InputStream() {
			@Override
			public int read() throws IOException {
				if (itr.hasNext()) {
					return itr.next();
				} else {
					return -1;
				}

			}
		};
		ConsoleMenu menu = new InventoryItemMenu();
		ConsoleMenu menu1 = new SubMenu("Sub Menu");
		menu.addMenu(menu1);

		menu.readInput(new Scanner(is));
		assertSame(menu1, menu.getActiveMenu());
	}
}
