package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

final class InMemoryInventoryItemStorage implements InventoryItemRepository {
	private static final AtomicLong itemId = new AtomicLong(0);
	private final Map<Long, InventoryItem> db;
	private final Map<InventoryItem, InventoryCount> counts;

	InMemoryInventoryItemStorage() {
		db = new HashMap<>();
		counts = new HashMap<>();
	}

	@Override
	public Optional<InventoryItem> loadItem(Long id) {
		if (db.containsKey(id)) {
			return Optional.of(new InventoryItem(db.get(id)));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItem> updateItem(InventoryItem item) {
		if (db.containsKey(item.getId())) {
			InventoryItem updatedItem = new InventoryItem(item);
			db.replace(item.getId(), updatedItem);
			return Optional.of(new InventoryItem(updatedItem));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItem> saveItem(InventoryItem item) {
		Long id = InMemoryInventoryItemStorage.itemId.getAndIncrement();
		InventoryItem newItem = new InventoryItem(item);
		newItem.setId(id);
		db.put(id, newItem);
		counts.put(newItem, new InventoryCount(newItem.getId(), newItem.getName(), newItem.getCategory(), item.getLocation()));
		return Optional.of(new InventoryItem(newItem));
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
		return db.values().stream()
				.map(InventoryItem::new)
				.collect(Collectors.toList());
	}

	@Override
	public List<InventoryItem> loadActiveItems() {
		return db.values().stream()
				.filter(InventoryItem::isActive)
				.map(InventoryItem::new)
				.collect(Collectors.toList());
	}

	@Override
	public List<InventoryCount> loadCounts() {
		return db.values().stream()
				.filter(InventoryItem::isActive)
				.flatMap(item -> Stream.of(counts.get(item)))
				.collect(Collectors.toList());
	}

	@Override
	public void updateCount(InventoryCount count) {
		if (db.containsKey(count.getItemId())) {
			counts.replace(db.get(count.getItemId()), new InventoryCount(count));
		}
	}

	@Override
	public Optional<InventoryCount> loadCount(Long id) {
		return loadItem(id)
				.map(counts::get);
	}
}
