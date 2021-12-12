package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface InventoryItemStorage {

	Optional<InventoryItem> loadItem(Long id);

	Optional<InventoryItem> updateItem(InventoryItem item);

	Long createItem(InventoryItem item);

	boolean deleteItem(Long itemId);

	List<InventoryItem> loadAllItems();

	List<InventoryItem> loadActiveItems();

	List<InventoryCount> loadCounts();

	default void updateCount(InventoryCount count) {
		throw new UnsupportedOperationException();
	}

	default Optional<InventoryCount> loadCount(Long id) {
		throw new UnsupportedOperationException();
	}
}
