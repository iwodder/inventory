package com.wodder.inventory.domain;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

class ItemStorageService implements ItemStorage {

	private final InventoryItemStorage store;

	ItemStorageService(InventoryItemStorage store) {
		this.store = store;
	}

	ItemStorageService() {
		this(null);
	}

	@Override
	public Optional<InventoryItemModel> addItem(InventoryItemModel newItem) {
		if (newItem.getId() != null || newItem.getName() == null) return Optional.empty();

		InventoryItem item = new InventoryItem(newItem);
		item.setActive(true);
		item.setId(store.createItem(item));

		return Optional.of(item.toItemModel());
	}

	@Override
	public Boolean deleteItem(InventoryItemModel itemToDelete) {
		if (itemToDelete.getId() == null) return false;

		return store.deleteItem(itemToDelete.getId());
	}

	@Override
	public Optional<InventoryItemModel> updateItem(InventoryItemModel updatedItem) {
		if (updatedItem.getId() == null) return Optional.empty();
		InventoryItem item = new InventoryItem(updatedItem);
		Optional<InventoryItem> result = store.updateItem(item);
		return result.map(this::convertDtoToItem);
	}

	@Override
	public Optional<InventoryItemModel> readItem(Long itemId) {
		if (itemId == null) return Optional.empty();
		Optional<InventoryItem> result = store.loadItem(itemId);
		return result.map(this::convertDtoToItem);
	}

	@Override
	public List<InventoryItemModel> readAllItems() {
		return store.loadAllItems()
				.stream()
				.map(this::convertDtoToItem)
				.collect(Collectors.toList());
	}

	private InventoryItemModel convertDtoToItem(InventoryItem item) {
		return InventoryItemModel.builder()
				.withCategory(item.getCategory())
				.withName(item.getName())
				.withId(item.getId())
				.build();
	}
}
