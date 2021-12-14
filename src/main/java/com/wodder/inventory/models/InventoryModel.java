package com.wodder.inventory.models;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class InventoryModel {
	private final BiPredicate<String, InventoryCountModel> locationFilter =
			(String location, InventoryCountModel model) -> location != null && location.equalsIgnoreCase(model.getLocation());

	private LocalDate inventoryDate;
	private final List<InventoryCountModel> inventoryItemModels;

	public InventoryModel() {
		inventoryItemModels = new ArrayList<>();
	}

	public void addInventoryCountModel(InventoryCountModel itemModel) {
		inventoryItemModels.add(itemModel);
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
		return inventoryItemModels.stream();
	}

	public Iterator<InventoryCountModel> itemsByLocation(String location) {
		return inventoryItemModels.stream().filter(i -> locationFilter.test(location, i)).iterator();
	}
}
