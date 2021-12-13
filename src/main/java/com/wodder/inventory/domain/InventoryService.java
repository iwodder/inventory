package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.time.*;
import java.util.*;

public class InventoryService {

	private final InventoryItemStorage inventoryItemStorage;
	private final InventoryStorage inventoryStorage;

	public InventoryService(InventoryItemStorage items, InventoryStorage inventoryStorage) {
		this.inventoryItemStorage = items;
		this.inventoryStorage = inventoryStorage;
	}

	public InventoryModel createNewInventory() {
		InventoryModel i = new InventoryModel();
		inventoryItemStorage.loadCounts()
				.stream()
				.map(InventoryCount::toModel)
				.forEach(i::addInventoryCountModel);
		return i;
	}

	public boolean saveInventory(InventoryModel i) {
		if (i.numberOfItems() == 0 || i.getInventoryDate().isBefore(LocalDate.now())) {
			return false;
		}

		return inventoryStorage.save(new Inventory(i));
	}

	public Optional<InventoryModel> loadInventory(LocalDate date) {
		if (date != null && date.isBefore(LocalDate.now().plusDays(1))) {
			return inventoryStorage.load(new Inventory(date)).map(Inventory::toModel);
		} else {
			return Optional.empty();
		}
	}
}
