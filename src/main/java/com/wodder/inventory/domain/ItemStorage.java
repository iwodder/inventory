package com.wodder.inventory.domain;

import com.wodder.inventory.models.*;

import java.util.*;

public interface ItemStorage {

	Optional<InventoryItemModel> addItem(InventoryItemModel newItem);

	Boolean deleteItem(InventoryItemModel itemToDelete);

	Optional<InventoryItemModel> updateItem(InventoryItemModel updatedItem);

	Optional<InventoryItemModel> readItem(Long itemId);

	List<InventoryItemModel> readAllItems();
}
