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
	public Optional<InventoryItemModel> updateItemCategory(InventoryItemModel updateModel) {
		if (updateModel.getId() == null) {
			return Optional.empty();
		} else {
			return processUpdate(updateModel);
		}
	}

	private Optional<InventoryItemModel> processUpdate(InventoryItemModel updateModel) {
		Optional<InventoryItem> inventoryItem = store.loadItem(updateModel.getId());
		Optional<Category> category = store.loadCategory(updateModel.getCategory());
		if (inventoryItem.isPresent() && category.isPresent()) {
			InventoryItem item = inventoryItem.get();
			InventoryItem updatedItem = item.updateCategory(category.get());
			return store.updateItem(updatedItem).map(InventoryItem::toItemModel);
		} else {
			return Optional.empty();
		}
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
