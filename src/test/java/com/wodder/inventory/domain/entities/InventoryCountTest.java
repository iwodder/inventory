package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryCountTest {

	@Test
	@DisplayName("Inventory must be created with positive count value")
	void positive_count() {
		assertThrows(IllegalArgumentException.class, () -> {
			new InventoryCount(null, null, null, null, -1);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			new InventoryCount(new InventoryCountModel(null, null,null,null, -1));
		});
	}
}
