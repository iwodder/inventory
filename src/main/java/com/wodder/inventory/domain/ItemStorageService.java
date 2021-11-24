package com.wodder.inventory.domain;

import com.wodder.inventory.dtos.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

class ItemStorageService implements ItemStorage {

	private final InventoryItems store;

	ItemStorageService(InventoryItems store) {
		this.store = store;
	}

	ItemStorageService() {
		this(null);
	}

	@Override
	public Optional<InventoryItemDto> addItem(InventoryItemDto newItem) {
		if (newItem.getId() != null || newItem.getName() == null) return Optional.empty();

		InventoryItem item = new InventoryItem(newItem);
		item.setActive(true);
		item.setId(store.createItem(item));

		return Optional.of(item.toDto());
	}

	@Override
	public Boolean deleteItem(InventoryItemDto itemToDelete) {
		if (itemToDelete.getId() == null) return false;

		return store.deleteItem(itemToDelete.getId());
	}

	@Override
	public Optional<InventoryItemDto> updateItem(InventoryItemDto updatedItem) {
		if (updatedItem.getId() == null) return Optional.empty();
		InventoryItem item = new InventoryItem(updatedItem);
		Optional<InventoryItem> result = store.updateItem(item);
		return result.map(this::convertDtoToItem);
	}

	@Override
	public Optional<InventoryItemDto> readItem(Long itemId) {
		if (itemId == null) return Optional.empty();
		Optional<InventoryItem> result = store.loadItem(itemId);
		return result.map(this::convertDtoToItem);
	}

	@Override
	public List<InventoryItemDto> readAllItems() {
		return store.loadAllItems()
				.stream()
				.map(this::convertDtoToItem)
				.collect(Collectors.toList());
	}

	private InventoryItemDto convertDtoToItem(InventoryItem item) {
		return InventoryItemDto.builder()
				.withCategory(item.getCategory())
				.withName(item.getName())
				.withId(item.getId())
				.build();
	}
}
