package com.wodder.inventory.application;

import com.wodder.inventory.models.*;

import java.util.*;

public interface ItemService {

	Optional<ProductModel> createNewItem(ProductModel newItem);

	Optional<ProductModel> createNewItem(String name, String category, String location, String unit, int unitsPerCase, String unitPrice, String casePrice);

	Boolean deleteItem(ProductModel itemToDelete);

	Optional<ProductModel> updateItemCategory(Long inventoryItemId, String category);

	Optional<ProductModel> updateItemLocation(Long inventoryItemId, String location);

	Optional<ProductModel> updateItemName(Long inventoryItemId, String name);

	Optional<ProductModel> updateItemUnitOfMeasurement(Long inventoryItemId, String unitOfMeasurement, Integer unitsPerCase);

	Optional<ProductModel> updateItemPrice(Long inventoryItemId, String unitPrice, String casePrice);

	Optional<ProductModel> loadItem(Long itemId);

	List<ProductModel> loadAllActiveItems();
}
