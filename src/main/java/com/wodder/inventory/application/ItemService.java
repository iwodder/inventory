package com.wodder.inventory.application;

import com.wodder.inventory.models.*;

import java.util.*;

public interface ItemService {

	Optional<ProductModel> createNewItem(ProductModel newItem);

	Optional<ProductModel> createNewItem(String name, String category, String location, String unit, int unitsPerCase, String unitPrice, String casePrice);

	Boolean deleteItem(String productId);

	Optional<ProductModel> updateItemCategory(String inventoryItemId, String category);

	Optional<ProductModel> updateItemLocation(String inventoryItemId, String location);

	Optional<ProductModel> updateItemName(String inventoryItemId, String name);

	Optional<ProductModel> updateItemUnitOfMeasurement(String inventoryItemId, String unitOfMeasurement, Integer unitsPerCase);

	Optional<ProductModel> updateItemPrice(String inventoryItemId, String unitPrice, String casePrice);

	Optional<ProductModel> loadItem(String itemId);

	List<ProductModel> loadAllActiveItems();
}
