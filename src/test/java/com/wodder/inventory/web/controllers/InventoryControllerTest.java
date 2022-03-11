package com.wodder.inventory.web.controllers;

import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

	DataPopulation p = new DataPopulation();

	@BeforeEach
	void setUp() {
		p.init();
	}

	@Test
	@DisplayName("Groups active items by category")
	void init() {
		InventoryController c = new InventoryController();
		c.init();
		assertNotNull(c.getItems());
	}

	@Test
	void getInventoryDate() {
	}
}
