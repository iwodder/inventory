package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.*;

import java.util.*;
import java.util.concurrent.atomic.*;

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
		if (db.containsKey(1L)) {
			InventoryItem updatedItem = new InventoryItem(item.getId(), item.getName(), null);
			db.replace(item.getId(), updatedItem);
			return Optional.of(db.get(item.getId()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long createItem(InventoryItem item) {
		Long itemId = id.addAndGet(1);
		InventoryItem newItem = new InventoryItem(itemId, item.getName(), item.getCategory());
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
		return Collections.unmodifiableList(new ArrayList<>(db.values()));
	}
}
