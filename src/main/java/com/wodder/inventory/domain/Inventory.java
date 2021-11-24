package com.wodder.inventory.domain;

import java.time.*;
import java.util.*;

public class Inventory {
	private final LocalDate date;
	private final Map<String, List<InventoryItem>> items;

	public Inventory() {
		date = LocalDate.now();
		items = new HashMap<>();
	}

	public LocalDate date() {
		return date;
	}

	void addInventoryItem(InventoryItem item) {
		if (item == null) return;
		String key = item.getCategory().toUpperCase();
		if (items.containsKey(key)) {
			items.get(key).add(item);
		} else {
			List<InventoryItem> i = new ArrayList<>();
			i.add(item);
			items.put(key, i);
		}
	}

	public int numberOfItems() {
		return items.values().stream().mapToInt(List::size).sum();
	}

	public List<InventoryItem> getItemsByCategory(String category) {
		if (items.containsKey(category.toUpperCase())) {
			return Collections.unmodifiableList(items.get(category.toUpperCase()));
		} else {
			return Collections.emptyList();
		}
	}

	public LocalDate getInventoryDate() {
		return date;
	}
}
