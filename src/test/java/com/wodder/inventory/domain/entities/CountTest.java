package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CountTest {

	@Test
	@DisplayName("Inventory must be created with positive count value")
	void positive_count() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Count(null, null, null, null, -1);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			new Count(new InventoryCountModel(null, null,null,null, -1));
		});
	}
}
