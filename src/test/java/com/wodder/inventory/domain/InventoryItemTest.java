package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

	@Test
	void inventory_item_is_equal_based_on_desc() {
		InventoryItem inv = new InventoryItem(1L, "2% Milk", "Dry Goods");
		InventoryItem inv2 = new InventoryItem(2L,"2% Milk", "Chemicals");
		assertEquals(inv, inv2);
	}

}
