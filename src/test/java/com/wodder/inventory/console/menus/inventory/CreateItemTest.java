package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateItemTest extends BaseMenuTest {

	@BeforeEach
	void setup() {
		super.setup();
		success = true;
	}

	@Test
	@DisplayName("Handles new item input")
	void handleInput() {
		chars = String.format("name=\"2%% Milk\"%n").chars().iterator();
		TestInventoryItemModel model = new TestInventoryItemModel();
		CreateItemMenu menu = new CreateItemMenu(model);
		menu.handleInput(in, out, null);
		assertNotNull(model.dto);
		assertEquals("2% Milk", model.dto.getName());
	}

	@Test
	@DisplayName("Creates InventoryItemDTO")
	void handle_input1() {
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		TestInventoryItemModel model = new TestInventoryItemModel();
		CreateItemMenu menu = new CreateItemMenu(model);
		menu.handleInput(in, out, null);
		assertEquals("2% Milk", model.dto.getName());
		assertEquals("refridgerated", model.dto.getCategory());
		assertNotNull(model.dto.getId());
	}

	@Test
	@DisplayName("On success menu prints id and name of created item")
	void on_success() {
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		CreateItemMenu menu = new CreateItemMenu(new TestInventoryItemModel());
		menu.handleInput(in, out, null);
		String result = baosOut.toString();
		assertEquals("Successfully created 2% Milk with id of 1"+System.lineSeparator(), result);
	}

	@Test
	@DisplayName("On error menu prints error reason")
	void on_error() {
		success = false;
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		CreateItemMenu menu = new CreateItemMenu(new TestInventoryItemModel());
		menu.handleInput(in, out, err);
		String result = baosOut.toString();
		assertEquals("Error"+System.lineSeparator(), result);
	}

	@Test
	@DisplayName("Prints menu")
	void prints_menu() {
		CreateItemMenu menu = new CreateItemMenu(new TestInventoryItemModel());
		menu.printMenu(out);
		String result = baosOut.toString();
		assertEquals(String.format("====== Create New Item ======%n"), result);
	}

	class TestInventoryItemModel implements InventoryItemModel {
		public InventoryItemDto dto;

		public Result<InventoryItemDto, String> createItem(InventoryItemDto dto) {
			this.dto = dto;
			dto.setId(1L);
			if (success) {
				return new Result<>(dto, null);
			} else {
				return new Result<>(null, "Error");
			}
		}
	}

}
