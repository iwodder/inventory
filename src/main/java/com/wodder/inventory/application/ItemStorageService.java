package com.wodder.inventory.application;

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
		if (newItem.getId() != null) return Optional.empty();

		Category category = store.loadCategory(newItem.getCategory()).orElseGet(Category::defaultCategory);
		Location location = store.loadLocation(newItem.getLocation()).orElseGet(Location::defaultLocation);
		InventoryItem item = new InventoryItem(newItem.getName(), category, location);

		return store.loadItem(store.createItem(item)).map(InventoryItem::toItemModel);
	}

	@Override
	public Boolean deleteItem(InventoryItemModel itemToDelete) {
		if (itemToDelete.getId() == null) return false;

		return store.deleteItem(itemToDelete.getId());
	}

	@Override
	public Optional<InventoryItemModel> updateItemCategory(InventoryItemModel updatedItem) {
		if (updatedItem.getId() == null) return Optional.empty();
		Optional<InventoryItem> opt = store.loadItem(updatedItem.getId());
		if (opt.isPresent()) {
			InventoryItem item = opt.get();

		}
		InventoryItem item = new InventoryItem(updatedItem);
		Optional<InventoryItem> result = store.updateItem(item);
		return result.map(InventoryItem::toItemModel);
	}

	@Override
	public Optional<InventoryItemModel> readItem(Long itemId) {
		if (itemId == null) return Optional.empty();
		Optional<InventoryItem> result = store.loadItem(itemId);
		return result.map(InventoryItem::toItemModel);
	}

	@Override
	public List<InventoryItemModel> readAllItems() {
		return store.loadAllItems()
				.stream()
				.map(InventoryItem::toItemModel)
				.collect(Collectors.toList());
	}
}
