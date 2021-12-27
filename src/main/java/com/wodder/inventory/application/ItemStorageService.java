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
	public Optional<InventoryItemModel> updateItemCategory(Long inventoryItemId, String category) {
		if (inventoryItemId == null) {
			return Optional.empty();
		} else {
			return processCategoryUpdate(inventoryItemId, category);
		}
	}

	@Override
	public Optional<InventoryItemModel> updateItemLocation(Long inventoryItemId, String location) {
		Optional<InventoryItem> opt = store.loadItem(inventoryItemId);
		Optional<Location> loc = store.loadLocation(location);
		if (opt.isPresent() && loc.isPresent()) {
			InventoryItem item = opt.get();
			item.updateLocation(loc.get());
			store.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItemModel> updateItemName(Long inventoryItemId, String name) {
		Optional<InventoryItem> opt = store.loadItem(1L);
		if (opt.isPresent()) {
			InventoryItem item = opt.get();
			item.updateName(name);
			store.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	private Optional<InventoryItemModel> processCategoryUpdate(Long inventoryItemId, String category) {
		Optional<InventoryItem> inventoryItem = store.loadItem(inventoryItemId);
		Optional<Category> c = store.loadCategory(category);
		if (inventoryItem.isPresent() && c.isPresent()) {
			InventoryItem item = inventoryItem.get();
			item.updateCategory(c.get());
			return store.updateItem(item).map(InventoryItem::toItemModel);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItemModel> loadItem(Long itemId) {
		if (itemId == null) return Optional.empty();
		return store.loadItem(itemId).map(InventoryItem::toItemModel);
	}

	@Override
	public List<InventoryItemModel> loadAllActiveItems() {
		return store.loadAllItems()
				.stream()
				.map(InventoryItem::toItemModel)
				.collect(Collectors.toList());
	}
}
