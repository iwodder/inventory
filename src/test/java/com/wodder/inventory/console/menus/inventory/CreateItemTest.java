package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateItemTest {
	private PrimitiveIterator.OfInt chars;

	@Test
	@DisplayName("Handles new item input")
	void handleInput() {
		chars = String.format("name=\"2%% Milk\"%n").chars().iterator();
		TestInventoryItemModel model = new TestInventoryItemModel();
		CreateItemMenu menu = new CreateItemMenu(model);
		menu.handleInput(new Scanner(new TestInputStream()), null);
		assertNotNull(model.dto);
		assertEquals("2% Milk", model.dto.getName());
	}

	@Test
	@DisplayName("Creates InventoryItemDTO")
	void handle_input1() {
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		TestInventoryItemModel model = new TestInventoryItemModel();
		CreateItemMenu menu = new CreateItemMenu(model);
		menu.handleInput(new Scanner(new TestInputStream()), null);
		assertEquals("2% Milk", model.dto.getName());
		assertEquals("refridgerated", model.dto.getCategory());
		assertNull(model.dto.getId());
	}

	@Test
	@DisplayName("Prints menu")
	void prints_menu() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CreateItemMenu menu = new CreateItemMenu(new TestInventoryItemModel());
		menu.printMenu(new PrintStream(baos));
		String result = baos.toString();
		assertEquals(String.format("====== Create New Item ======%n"), result);
	}

	class TestInventoryItemModel implements InventoryItemModel {
		public InventoryItemDto dto;

		public void createItem(InventoryItemDto dto) {
			this.dto = dto;
		}

		@Override
		public void registerListener(Listener listener) {

		}
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
