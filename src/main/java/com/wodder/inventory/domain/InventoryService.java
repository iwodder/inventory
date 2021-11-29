package com.wodder.inventory.domain;

import com.wodder.inventory.persistence.*;

import java.time.*;

public class InventoryService {

	private final InventoryItems inventoryItems;
	private final InventoryStorage inventoryStorage;

	public InventoryService(InventoryItems items, InventoryStorage inventoryStorage) {
		this.inventoryItems = items;
		this.inventoryStorage = inventoryStorage;
	}

	public Inventory createNewInventory() {
		Inventory i = new Inventory();
		inventoryItems.loadActiveItems().forEach(i::addInventoryItem);
		return i;
	}

	public boolean saveInventory(Inventory i) {
		if (i.numberOfItems() == 0 || i.date().isBefore(LocalDate.now())) {
			return false;
		}
		return inventoryStorage.save(i);
	}
}
