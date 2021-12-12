package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

	@Test
	void inventory_item_is_equal_based_on_desc() {
		InventoryItem inv = new InventoryItem(1L, "2% Milk", "Dry Goods");
		InventoryItem inv2 = new InventoryItem(2L,"2% Milk", "Chemicals");
		assertEquals(inv, inv2);
	}

	@Test
	@DisplayName("Setting negative count causes illegal argument exception")
	void negative_count() {
		InventoryItem inv = new InventoryItem(null,null,null);
		assertThrows(IllegalArgumentException.class, () -> inv.setOnHandQty(-1));
	}

	@Test
	@DisplayName("Coverts inventory item to count model")
	void count_model() {
		InventoryItem i = new InventoryItem(1L, "2% Milk", "Dry Goods");
		InventoryCountModel model = i.toCountModel();
		assertEquals(i.getId(), model.getItemId());
		assertEquals(i.getName(), model.getName());
		assertEquals(i.getCategory(), model.getCategory());
		assertEquals(i.getOnHandQty(), model.getOnHandQty());
	}
}
