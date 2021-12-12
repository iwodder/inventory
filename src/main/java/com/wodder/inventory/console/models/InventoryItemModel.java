package com.wodder.inventory.console.models;

import com.wodder.inventory.models.*;

import java.util.*;

public interface InventoryItemModel {

	Result<com.wodder.inventory.models.InventoryItemModel, String> createItem(com.wodder.inventory.models.InventoryItemModel itemDto);

	Result<Boolean, String> deleteItem(com.wodder.inventory.models.InventoryItemModel itemDto);

	Result<com.wodder.inventory.models.InventoryItemModel, String> updateItem(com.wodder.inventory.models.InventoryItemModel itemDto);

	Result<List<com.wodder.inventory.models.InventoryItemModel>, String> getItems();
}
