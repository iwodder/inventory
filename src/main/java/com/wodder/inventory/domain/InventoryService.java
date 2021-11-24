package com.wodder.inventory.domain;

import com.wodder.inventory.persistence.*;

public class InventoryService {

	private final InventoryItems inventoryItems;

	public InventoryService(InventoryItems items) {
		this.inventoryItems = items;
	}

	public Inventory createNewInventory() {
		Inventory i = new Inventory();
		inventoryItems.loadActiveItems().forEach(i::addInventoryItem);
		return i;
	}
}
