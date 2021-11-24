package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

final class InMemoryInventoryItems implements InventoryItems {
	private static final AtomicLong id = new AtomicLong(0);
	private final Map<Long, InventoryItem> db;

	InMemoryInventoryItems() {
		db = new HashMap<>();
	}

	@Override
	public Optional<InventoryItem> loadItem(Long id) {
		if (db.containsKey(id)) {
			return Optional.of(db.get(id));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItem> updateItem(InventoryItem item) {
		if (db.containsKey(item.getId())) {
			InventoryItem updatedItem = new InventoryItem(item);
			db.replace(item.getId(), updatedItem);
			return Optional.of(db.get(item.getId()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long createItem(InventoryItem item) {
		Long itemId = id.addAndGet(1);
		InventoryItem newItem = new InventoryItem(item);
		newItem.setId(itemId);
		db.put(itemId, newItem);
		return itemId;
	}

	@Override
	public boolean deleteItem(Long itemId) {
		if (itemId != null) {
			db.remove(itemId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<InventoryItem> loadAllItems() {
		return List.copyOf(db.values());
	}

	@Override
	public List<InventoryItem> loadActiveItems() {
		return db.values().stream()
				.filter(InventoryItem::isActive)
				.collect(Collectors.toUnmodifiableList());
	}
}
