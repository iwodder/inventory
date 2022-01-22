package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

class ItemStorageService implements ItemStorage {

	private final InventoryItemRepository inventoryItemRepository;
	private final Repository<Category> categoryRepository;
	private final Repository<Location> locationRepository;

	ItemStorageService(InventoryItemRepository itemRepository, Repository<Category> categoryRepository, Repository<Location> locationRepository) {
		this.inventoryItemRepository = itemRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
	}

	ItemStorageService() {
		this(null, null, null);
	}

	@Override
	public Optional<InventoryItemModel> createNewItem(InventoryItemModel newItem) {
		return createNewItem(newItem.getName(), newItem.getCategory(), newItem.getLocation(), newItem.getUnits(), newItem.getUnitsPerCase(), newItem.getItemPrice(), newItem.getCasePrice());
	}

	@Override
	public Optional<InventoryItemModel> createNewItem(String name, String category, String location, String unit, int unitsPerCase, String unitPrice, String casePrice) {
		Optional<Category> cat = categoryRepository.loadByItem(new Category(category));
		Optional<Location> loc = locationRepository.loadByItem(new Location(location));
		if (cat.isPresent() && loc.isPresent()) {
			InventoryItem item = new InventoryItem(
					name, cat.get(), loc.get(), new UnitOfMeasurement(unit, unitsPerCase),
					new Price(unitPrice, casePrice));
			return inventoryItemRepository.saveItem(item).map(InventoryItem::toItemModel);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean deleteItem(InventoryItemModel itemToDelete) {
		if (itemToDelete.getId() == null) return false;
		return inventoryItemRepository.deleteItem(itemToDelete.getId());
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
		Optional<InventoryItem> opt = inventoryItemRepository.loadItem(inventoryItemId);
		Optional<Location> loc = locationRepository.loadByItem(new Location(location));
		if (opt.isPresent() && loc.isPresent()) {
			InventoryItem item = opt.get();
			item.updateLocation(loc.get());
			inventoryItemRepository.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItemModel> updateItemName(Long inventoryItemId, String name) {
		Optional<InventoryItem> opt = inventoryItemRepository.loadItem(1L);
		if (opt.isPresent()) {
			InventoryItem item = opt.get();
			item.updateName(name);
			inventoryItemRepository.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItemModel> updateItemUnitOfMeasurement(Long inventoryItemId, String unitOfMeasurement, Integer unitsPerCase) {
		Optional<InventoryItem> opt = inventoryItemRepository.loadItem(inventoryItemId);
		if (opt.isPresent()) {
			InventoryItem item = opt.get();
			item.updateUnitOfMeasurement(new UnitOfMeasurement(unitOfMeasurement, unitsPerCase));
			inventoryItemRepository.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItemModel> updateItemPrice(Long inventoryItemId, String unitPrice, String casePrice) {
		Optional<InventoryItem> opt = inventoryItemRepository.loadItem(inventoryItemId);
		if (opt.isPresent()) {
			InventoryItem i = opt.get();
			i.updatePrice(new Price(unitPrice, casePrice));
			inventoryItemRepository.updateItem(i);
			return Optional.of(i.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	private Optional<InventoryItemModel> processCategoryUpdate(Long inventoryItemId, String category) {
		Optional<InventoryItem> inventoryItem = inventoryItemRepository.loadItem(inventoryItemId);
		Optional<Category> c = categoryRepository.loadByItem(new Category(category));
		if (inventoryItem.isPresent() && c.isPresent()) {
			InventoryItem item = inventoryItem.get();
			item.updateCategory(c.get());
			return inventoryItemRepository.updateItem(item).map(InventoryItem::toItemModel);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryItemModel> loadItem(Long itemId) {
		if (itemId == null) return Optional.empty();
		return inventoryItemRepository.loadItem(itemId).map(InventoryItem::toItemModel);
	}

	@Override
	public List<InventoryItemModel> loadAllActiveItems() {
		return inventoryItemRepository.loadAllItems()
				.stream()
				.map(InventoryItem::toItemModel)
				.collect(Collectors.toList());
	}
}
