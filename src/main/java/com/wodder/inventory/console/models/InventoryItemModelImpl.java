package com.wodder.inventory.console.models;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.*;

import java.util.*;

public class InventoryItemModelImpl implements InventoryItemModel {
	private final ItemStorage storage;

	public InventoryItemModelImpl(ItemStorage itemStorage) {
		this.storage = itemStorage;
	}

	@Override
	public Result<com.wodder.inventory.models.InventoryItemModel, String> createItem(com.wodder.inventory.models.InventoryItemModel itemDto) {
		Optional<com.wodder.inventory.models.InventoryItemModel> result = storage.addItem(itemDto);
		return result.<Result<com.wodder.inventory.models.InventoryItemModel, String>>map(inventoryItemDto -> new Result<>(inventoryItemDto, null))
				.orElseGet(() -> new Result<>(null, "Unable to create new item"));
	}

	@Override
	public Result<Boolean, String> deleteItem(com.wodder.inventory.models.InventoryItemModel itemDto) {
		boolean result = storage.deleteItem(itemDto);
		if (result) {
			return new Result<>(Boolean.TRUE, null);
		} else {
			return new Result<>(null, "Unable to delete item");
		}
	}

	@Override
	public Result<com.wodder.inventory.models.InventoryItemModel, String> updateItem(com.wodder.inventory.models.InventoryItemModel itemDto) {
		Optional<com.wodder.inventory.models.InventoryItemModel> result = storage.updateItem(itemDto);
		return result.<Result<com.wodder.inventory.models.InventoryItemModel, String>>map(i -> new Result<>(i, null))
				.orElseGet(() -> new Result<>(null, "Unable to update item"));
	}

	@Override
	public Result<List<com.wodder.inventory.models.InventoryItemModel>, String> getItems() {
		List<com.wodder.inventory.models.InventoryItemModel> items = storage.readAllItems();
		return items.isEmpty() ?
				new Result<>(null, "Unable to access items") :
				new Result<>(items, null);
	}
}
