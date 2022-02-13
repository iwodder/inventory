package com.wodder.inventory.application;

import com.wodder.inventory.models.*;

import java.util.*;

public interface ItemService {

	Optional<InventoryItemModel> createNewItem(InventoryItemModel newItem);

	Optional<InventoryItemModel> createNewItem(String name, String category, String location, String unit, int unitsPerCase, String unitPrice, String casePrice);

	Boolean deleteItem(InventoryItemModel itemToDelete);

	Optional<InventoryItemModel> updateItemCategory(Long inventoryItemId, String category);

	Optional<InventoryItemModel> updateItemLocation(Long inventoryItemId, String location);

	Optional<InventoryItemModel> updateItemName(Long inventoryItemId, String name);

	Optional<InventoryItemModel> updateItemUnitOfMeasurement(Long inventoryItemId, String unitOfMeasurement, Integer unitsPerCase);

	Optional<InventoryItemModel> updateItemPrice(Long inventoryItemId, String unitPrice, String casePrice);

	Optional<InventoryItemModel> loadItem(Long itemId);

	List<InventoryItemModel> loadAllActiveItems();
}