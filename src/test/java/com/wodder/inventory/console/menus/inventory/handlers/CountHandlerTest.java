package com.wodder.inventory.console.menus.inventory.handlers;

import com.wodder.inventory.console.menus.inventoryitems.*;
import com.wodder.inventory.domain.*;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountHandlerTest extends BaseMenuTest {

	@Mock
	private InventoryService service;

	@InjectMocks
	CountHandler handler;

	private InventoryModel model;

	@BeforeEach
	protected void setup() {
		super.setup();
		setupInventoryModel();
	}

	private void setupInventoryModel() {
		model = new InventoryModel();
		model.setInventoryDate(LocalDate.now());
		model.addInventoryCountModel(new InventoryCountModel(1L, "Bananas", "Fruit", "Pantry"));
		model.addInventoryCountModel(new InventoryCountModel(2L, "Apples", "Fruit", "Pantry"));
		model.addInventoryCountModel(new InventoryCountModel(3L, "Spinach", "Vegetables", "Refrigerator"));
		model.addInventoryCountModel(new InventoryCountModel(4L, "Frozen Broccoli", "Frozen", "Freezer"));
	}

	@Test
	@DisplayName("Entering \"exit\" leaves menu")
	void handleInput() {
		setChars("exit");
		handler.handleInput(in,out,err);
		assertTrue(true);
	}

	@Test
	@DisplayName("User chooses which location to count")
	void choose_location() {
		setChars("pantry\nexit");
		handler.handleInput(in,out,err);
		assertEquals(String.format("---- Counting Pantry ----%n"), baosOut.toString());
	}
}
