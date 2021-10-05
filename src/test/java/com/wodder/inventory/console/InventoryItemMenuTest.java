package com.wodder.inventory.console;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemMenuTest {
	private InventoryItemMenu menu;
	private ByteArrayOutputStream baos;
	private PrintStream output;
	private TestInputStream testIs;
	private Scanner input;
	private final String menuText = "Inventory Item Menu\n1) Create new item\n2) Update existing item\n3) Delete item\n4) List all items";
	private

	@BeforeEach
	void setup() {
		baos = new ByteArrayOutputStream();
		output = new PrintStream(baos);
		testIs = new TestInputStream();
		input = new Scanner(testIs);
		menu = new InventoryItemMenu("Inventory Item Menu");
	}

	@Test
	@DisplayName("Prints the inventory item menu")
	void menu() {
		menu.printMenu(output);
		String text = baos.toString();
		assertEquals(menuText, text);
	}

	@Test
	@DisplayName("Can print new item text")
	void prints_new_item() {
		testIs.input = Arrays.asList('1', '\n', '5', '\n').iterator();
		menu.readInput(input);
		String output = baos.toString();
		assertTrue(output.startsWith("Creating new item"));
	}

	@Test
	@DisplayName("Can print delete item text")
	void prints_deletes_item() {
		testIs.input = Arrays.asList('3', '\n',  '5', '\n').iterator();
		menu.readInput(input);
		String output = baos.toString();
		assertTrue(output.startsWith("Deleting existing item"));
	}

	@Test
	@DisplayName("Can print update item text")
	void prints_update_item() {
		testIs.input = Arrays.asList('2', '\n', '5', '\n').iterator();
		menu.readInput(input);
		String output = baos.toString();
		assertTrue(output.startsWith("Updating existing item"));
	}

	@Test
	@DisplayName("Can print all items text")
	void prints_all_item() {
		testIs.input = Arrays.asList('4', '\n',  '5', '\n').iterator();
		menu.readInput(input);
		String output = baos.toString();
		assertTrue(output.startsWith("Listing all items"));
	}

	@Test
	@DisplayName("Entering non-number to menu select prints help text")
	void non_number_test() {
		testIs.input = Arrays.asList('a', '\n', '5', '\n').iterator();
		menu.readInput(input);
		String[] output = baos.toString().split("\n");
		assertTrue(output[0].startsWith("Please enter a number"));
		assertTrue(output[1].startsWith("Exiting"));
	}
	
	private static class TestInputStream extends InputStream {
		protected Iterator<Character> input;

		@Override
		public int read() throws IOException {
			if (input.hasNext()) {
				return input.next();
			} else {
				return -1;
			}
		}
	}
}
