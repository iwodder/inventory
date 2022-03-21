package com.wodder.inventory.models;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class InventoryModel implements Serializable {

	private LocalDate inventoryDate;
	private final Map<String, InventoryCountModel> inventoryItemModels;
	private final String state;
	private final String id;

	public InventoryModel(String id, String state) {
		this.id = id;
		this.state = state;
		inventoryItemModels = new HashMap<>();
	}

	public InventoryModel() {
		this("", "OPEN");
	}

	public void addInventoryCountModel(InventoryCountModel itemModel) {
		inventoryItemModels.put(itemModel.getProductId(), itemModel);
	}

	public LocalDate getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public int numberOfItems() {
		return inventoryItemModels.size();
	}

	public Stream<InventoryCountModel> items() {
		return inventoryItemModels.values().stream();
	}

	public String getState() {
		return state;
	}

	public String getId() {
		return id;
	}
}
