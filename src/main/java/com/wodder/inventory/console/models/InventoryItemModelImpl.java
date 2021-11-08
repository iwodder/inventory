package com.wodder.inventory.console.models;

import com.wodder.inventory.domain.*;
import com.wodder.inventory.dtos.*;

import java.util.*;

public class InventoryItemModelImpl implements InventoryItemModel {
	private final ItemStorage storage;

	public InventoryItemModelImpl(ItemStorage itemStorage) {
		this.storage = itemStorage;
	}

	@Override
	public Result<InventoryItemDto, String> createItem(InventoryItemDto itemDto) {
		Optional<InventoryItemDto> result = storage.addItem(itemDto);
		return result.<Result<InventoryItemDto, String>>map(inventoryItemDto -> new Result<>(inventoryItemDto, null))
				.orElseGet(() -> new Result<>(null, "Unable to create new item"));
	}

	@Override
	public Result<Boolean, String> deleteItem(InventoryItemDto itemDto) {
		boolean result = storage.deleteItem(itemDto);
		if (result) {
			return new Result<>(Boolean.TRUE, null);
		} else {
			return new Result<>(null, "Unable to delete item");
		}
	}

	@Override
	public Result<InventoryItemDto, String> updateItem(InventoryItemDto itemDto) {
		Optional<InventoryItemDto> result = storage.updateItem(itemDto);
		return result.<Result<InventoryItemDto, String>>map(i -> new Result<>(i, null))
				.orElseGet(() -> new Result<>(null, "Unable to update item"));
	}
}
