package com.wodder.inventory.application;

import com.wodder.inventory.models.*;

import java.util.*;

public interface ItemStorage {

	Optional<InventoryItemModel> addItem(InventoryItemModel newItem);

	Boolean deleteItem(InventoryItemModel itemToDelete);

	Optional<InventoryItemModel> updateItemCategory(InventoryItemModel updatedItem);

	Optional<InventoryItemModel> readItem(Long itemId);

	List<InventoryItemModel> readAllItems();
}
