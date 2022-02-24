package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

class ItemServiceImpl implements ItemService {

	private final Repository<Product, ProductId> productRepository;
	private final Repository<Category, CategoryId> categoryRepository;
	private final Repository<Location, LocationId> locationRepository;

	ItemServiceImpl(Repository<Product, ProductId> productRepository, Repository<Category, CategoryId> categoryRepository, Repository<Location, LocationId> locationRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public Optional<ProductModel> createNewItem(ProductModel newItem) {
		return createNewItem(newItem.getName(), newItem.getCategory(), newItem.getLocation(), newItem.getUnits(), newItem.getUnitsPerCase(), newItem.getItemPrice(), newItem.getCasePrice());
	}

	@Override
	public Optional<ProductModel> createNewItem(String name, String category, String location, String unit, int unitsPerCase, String unitPrice, String casePrice) {
		Category cat = categoryRepository.loadByItem(new Category(category))
				.orElseGet(() -> categoryRepository.createItem(new Category(category)));

		Location loc = locationRepository.loadByItem(new Location(location))
				.orElseGet(() -> locationRepository.createItem(new Location(location)));

		Product item = new Product(
				name, cat, loc, new UnitOfMeasurement(unit, unitsPerCase),
				new Price(unitPrice, casePrice));

		return Optional.of(productRepository.createItem(item)).map(Product::toItemModel);
	}

	@Override
	public Boolean deleteItem(ProductModel itemToDelete) {
		if (itemToDelete.getId() == null) return false;
		return productRepository.deleteItem(ProductId.productIdOf(itemToDelete.getId()));
	}

	@Override
	public Optional<ProductModel> updateItemCategory(String inventoryItemId, String category) {
		if (inventoryItemId == null) {
			return Optional.empty();
		} else {
			return processCategoryUpdate(inventoryItemId, category);
		}
	}

	@Override
	public Optional<ProductModel> updateItemLocation(String inventoryItemId, String location) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(inventoryItemId));
		Optional<Location> loc = locationRepository.loadByItem(new Location(location));
		if (opt.isPresent() && loc.isPresent()) {
			Product item = opt.get();
			item.updateLocation(loc.get());
			productRepository.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ProductModel> updateItemName(String inventoryItemId, String name) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(inventoryItemId));
		if (opt.isPresent()) {
			Product item = opt.get();
			item.updateName(name);
			productRepository.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ProductModel> updateItemUnitOfMeasurement(String inventoryItemId, String unitOfMeasurement, Integer unitsPerCase) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(inventoryItemId));;
		if (opt.isPresent()) {
			Product item = opt.get();
			item.updateUnitOfMeasurement(new UnitOfMeasurement(unitOfMeasurement, unitsPerCase));
			productRepository.updateItem(item);
			return Optional.of(item.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ProductModel> updateItemPrice(String inventoryItemId, String unitPrice, String casePrice) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(inventoryItemId));
		if (opt.isPresent()) {
			Product i = opt.get();
			i.updatePrice(new Price(unitPrice, casePrice));
			productRepository.updateItem(i);
			return Optional.of(i.toItemModel());
		} else {
			return Optional.empty();
		}
	}

	private Optional<ProductModel> processCategoryUpdate(String inventoryItemId, String category) {
		Optional<Product> inventoryItem = productRepository.loadById(ProductId.productIdOf(inventoryItemId));
		Optional<Category> c = categoryRepository.loadByItem(new Category(category));
		if (inventoryItem.isPresent() && c.isPresent()) {
			Product item = inventoryItem.get();
			item.updateCategory(c.get());
			return Optional.of(productRepository.updateItem(item)).map(Product::toItemModel);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ProductModel> loadItem(String itemId) {
		if (itemId == null) return Optional.empty();
		return productRepository.loadById(ProductId.productIdOf(itemId)).map(Product::toItemModel);
	}

	@Override
	public List<ProductModel> loadAllActiveItems() {
		return productRepository.loadAllItems()
				.stream()
				.map(Product::toItemModel)
				.collect(Collectors.toList());
	}
}
