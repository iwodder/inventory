package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateItemHandlerTest extends BaseMenuTest {
	private boolean exited;

	@BeforeEach
	protected void setup() {
		super.setup();
		exited = false;
	}

	@Test
	@DisplayName("Entering exit calls exit on menu")
	void handles_exit() {
		setChars("exit");
		CreateItemHandler handler = new CreateItemHandler(null);
		handler.setMenu(new TestMenu());
		handler.handleInput(in, out, err);
		assertTrue(exited);
	}

	private class TestMenu extends ConsoleMenu {

		TestMenu() {
			super("Test Menu");
		}

		@Override
		public void exitMenu() {
			exited = true;
		}
	}
}
