package com.wodder.inventory.console.models;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.*;

import java.util.*;

public class InventoryItemModelImpl implements InventoryItemModel {
	private final ItemService storage;

	public InventoryItemModelImpl(ItemService itemService) {
		this.storage = itemService;
	}

	@Override
	public Result<ProductModel, String> createItem(ProductModel itemDto) {
		Optional<ProductModel> result = storage.createNewItem(itemDto);
		return result.<Result<ProductModel, String>>map(inventoryItemDto -> new Result<>(inventoryItemDto, null))
				.orElseGet(() -> new Result<>(null, "Unable to create new item"));
	}

	@Override
	public Result<Boolean, String> deleteItem(ProductModel itemDto) {
		boolean result = storage.deleteItem(itemDto);
		if (result) {
			return new Result<>(Boolean.TRUE, null);
		} else {
			return new Result<>(null, "Unable to delete item");
		}
	}

	@Override
	public Result<ProductModel, String> updateItem(ProductModel itemDto) {
		Optional<ProductModel> result = storage.updateItemCategory(itemDto.getId(), itemDto.getCategory());
		return result.<Result<ProductModel, String>>map(i -> new Result<>(i, null))
				.orElseGet(() -> new Result<>(null, "Unable to update item"));
	}

	@Override
	public Result<List<ProductModel>, String> getItems() {
		List<ProductModel> items = storage.loadAllActiveItems();
		return items.isEmpty() ?
				new Result<>(null, "Unable to access items") :
				new Result<>(items, null);
	}
}
