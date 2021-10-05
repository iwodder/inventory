package com.wodder.inventory.domain;

import java.time.*;
import java.util.*;

public class Inventory {
	private final LocalDate date;
	private final List<InventoryItem> items;

	public Inventory() {
		date = LocalDate.now();
		items = new ArrayList<>();
	}

	public LocalDate date() {
		return date;
	}

	public boolean addInventoryItem(InventoryItem item) {
		if (item == null) return false;

		return items.add(item);
	}

	public int numberOfItems() {
		return items.size();
	}
}
