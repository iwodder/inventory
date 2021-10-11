package com.wodder.inventory.console.menus.inventory;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateItemTest {
	private PrimitiveIterator.OfInt chars;

	@Test
	@DisplayName("Handles new item input")
	void handleInput() {
		chars = String.format("name=2%% Milk%n").chars().iterator();
		CreateItemMenu menu = new CreateItemMenu();
		menu.handleInput(new Scanner(new TestInputStream()), null);
		assertNotNull(menu.createdDto);
	}

	@Test
	@DisplayName("Creates InventoryItemDTO")
	void handle_input1() {
		chars = String.format("name=2%% Milk category=refridgerated%n").chars().iterator();
		CreateItemMenu menu = new CreateItemMenu();
		menu.handleInput(new Scanner(new TestInputStream()), null);
		assertEquals("2% Milk", menu.createdDto.getName());
		assertEquals("refridgerated", menu.createdDto.getCategory());
		assertNull(menu.createdDto.getId());
	}

	class TestInputStream extends InputStream {

		@Override
		public int read() throws IOException {
			if (chars.hasNext()) {
				return chars.next();
			} else {
				return -1;
			}
		}
	}
}
