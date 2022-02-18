package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public final class InMemoryInventoryRepository extends InMemoryRepository<Inventory> {

	InMemoryInventoryRepository() {}

	@Override
	public Inventory createItem(Inventory inv) {
		inv.setId(getNextId());
		Inventory inventory = new Inventory(inv);
		addItem(inventory);
		return inventory;
	}
}
