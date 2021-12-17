package com.wodder.inventory.console.menus.inventoryitems;

import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//TODO Update class to use mockito
class CreateItemMenuTest extends BaseMenuTest {
	private CreateItemMenu menu;
	private CreateItemHandler createItemHandler;
	private TestInventoryItemModel model;

	@BeforeEach
	protected void setup() {
		super.setup();
		success = true;

		model = new TestInventoryItemModel();
		createItemHandler = new CreateItemHandler(model);
		menu = new CreateItemMenu(createItemHandler, model);
	}

	@Test
	@DisplayName("Handles new item input")
	void handleInput() {
		chars = String.format("name=\"2%% Milk\"%n").chars().iterator();
		menu.process(in, out, null);
		assertNotNull(model.dto);
		assertEquals("2% Milk", model.dto.getName());
	}

	@Test
	@DisplayName("Creates InventoryItemDTO")
	void handle_input1() {
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		menu.process(in, out, null);
		assertEquals("2% Milk", model.dto.getName());
		assertEquals("refridgerated", model.dto.getCategory());
		assertNotNull(model.dto.getId());
	}

	@Test
	@DisplayName("On success menu prints id and name of created item")
	void on_success() {
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		menu.process(in, out, null);
		String result = baosOut.toString();
		assertEquals("Successfully created 2% Milk with id of 1"+System.lineSeparator(), result);
	}

	@Test
	@DisplayName("On error menu prints error reason")
	void on_error() {
		success = false;
		chars = String.format("name=\"2%% Milk\" category=refridgerated%n").chars().iterator();
		menu.process(in, out, err);
		String result = baosOut.toString();
		assertEquals("Error"+System.lineSeparator(), result);
	}

	@Test
	@DisplayName("Prints menu")
	void prints_menu() {
		menu.printMenu(out);
		String result = baosOut.toString();
		assertEquals(String.format("====== Create New Item ======%nEnter new item as key=value pairs, e.g. name=bread%nEnter 'exit' to return to the previous menu%n"), result);
	}

	@Test
	@DisplayName("Entering exit leaves this menu")
	void exit_menu() {
		setChars("exit");
		menu.process(in,out,err);
	}

	class TestInventoryItemModel implements com.wodder.inventory.console.models.InventoryItemModel {
		public InventoryItemModel dto;

		public Result<InventoryItemModel, String> createItem(InventoryItemModel dto) {
			this.dto = dto;
			dto.setId(1L);
			if (success) {
				return new Result<>(dto, null);
			} else {
				return new Result<>(null, "Error");
			}
		}

		@Override
		public Result<Boolean, String> deleteItem(InventoryItemModel itemDto) {
			return null;
		}

		@Override
		public Result<InventoryItemModel, String> updateItem(InventoryItemModel itemDto) {
			return null;
		}

		@Override
		public Result<List<InventoryItemModel>, String> getItems() {
			return null;
		}
	}
}