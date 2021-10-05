package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.*;

import java.util.*;

public interface InventoryItems {

	Optional<InventoryItem> loadItem(Long id);

	Optional<InventoryItem> updateItem(InventoryItem item);

	Long createItem(InventoryItem item);

	boolean deleteItem(Long itemId);

	List<InventoryItem> loadAllItems();
}
