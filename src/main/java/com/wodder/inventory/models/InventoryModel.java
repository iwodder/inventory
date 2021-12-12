package com.wodder.inventory.models;

import java.util.*;

public class InventoryModel {
	private final List<InventoryCountModel> inventoryItemModels;

	public InventoryModel() {
		inventoryItemModels = new ArrayList<>();
	}

	public void addInventoryItemModel(InventoryCountModel itemModel) {
		inventoryItemModels.add(itemModel);
	}

	public int numberOfItems() {
		return inventoryItemModels.size();
	}
}
