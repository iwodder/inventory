package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface InventoryItemRepository {

	Optional<InventoryItem> loadItem(Long id);

	Optional<InventoryItem> updateItem(InventoryItem item);

	Optional<InventoryItem> saveItem(InventoryItem item);

	boolean deleteItem(Long itemId);

	List<InventoryItem> loadAllItems();

	List<InventoryItem> loadActiveItems();

	List<InventoryCount> loadCounts();

	void updateCount(InventoryCount count);

	Optional<InventoryCount> loadCount(Long id);
}
