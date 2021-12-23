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

	void updateCount(InventoryCount count);

	Optional<InventoryCount> loadCount(Long id);

	Optional<Location> loadLocation(String name);

	Optional<Category> loadCategory(String name);
}
