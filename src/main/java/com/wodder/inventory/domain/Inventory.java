package com.wodder.inventory.domain;

import java.time.*;
import java.util.*;
import java.util.function.*;

public class Inventory {
	private final LocalDate date;
	private final Map<String, List<InventoryItem>> items;
	private final BiPredicate<InventoryItem, String> nameFilter = (InventoryItem i, String name) -> {
		if (i.getName() != null) {
			return i.getName().equals(name);
		} else {
			return false;
		}
	};

	public Inventory() {
		this(LocalDate.now());
	}

	public Inventory(LocalDate date) {
		items = new HashMap<>();
		this.date = date;
	}

	public Inventory(Inventory that) {
		this.date = that.date;
		this.items = new HashMap<>();
		for (String i : that.items.keySet()) {
			List<InventoryItem> newItems = new ArrayList<>();
			for (InventoryItem item : that.items.get(i)) {
				newItems.add(new InventoryItem(item));
			}
			this.items.put(i, newItems);
		}
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

	public Optional<InventoryItem> getInventoryItem(String name) {
		return items.values().stream()
				.flatMap(List::stream)
				.filter(item -> nameFilter.test(item, name))
				.findAny();
	}

	public LocalDate getInventoryDate() {
		return date;
	}
}
