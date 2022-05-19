package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.model.product.*;
import com.wodder.inventory.dto.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

class ProductServiceImpl implements ProductService {

	private final Repository<Product, ProductId> productRepository;
	private final Repository<Category, CategoryId> categoryRepository;
	private final Repository<Location, LocationId> locationRepository;

	ProductServiceImpl(Repository<Product, ProductId> productRepository, Repository<Category, CategoryId> categoryRepository, Repository<Location, LocationId> locationRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public Optional<ProductModel> createNewProduct(ProductModel newItem) {
		return createNewProduct(newItem.getName(), newItem.getCategory(), newItem.getLocation(), newItem.getUnits(), newItem.getUnitsPerCase(), newItem.getItemPrice(), newItem.getCasePrice());
	}

	@Override
	public Optional<ProductModel> createNewProduct(String name, String category, String location, String unit, int unitsPerCase, String unitPrice, String casePrice) {
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
	public Boolean deleteProduct(String productId) {
		return productRepository.deleteItem(ProductId.productIdOf(productId));
	}

	@Override
	public Optional<ProductModel> updateProductCategory(String productId, String category) {
		if (productId == null) {
			return Optional.empty();
		} else {
			return processCategoryUpdate(productId, category);
		}
	}

	@Override
	public Optional<ProductModel> updateProductLocation(String productId, String location) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
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
	public Optional<ProductModel> updateProductName(String productId, String name) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
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
	public Optional<ProductModel> updateProductUnitOfMeasurement(String productId, String unitOfMeasurement, Integer unitsPerCase) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));;
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
	public Optional<ProductModel> updateProductPrice(String productId, String unitPrice, String casePrice) {
		Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
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
			return productRepository.updateItem(item).map(Product::toItemModel);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ProductModel> loadProduct(String productId) {
		if (productId == null) return Optional.empty();
		return productRepository.loadById(ProductId.productIdOf(productId)).map(Product::toItemModel);
	}

	@Override
	public List<ProductModel> loadAllActiveProducts() {
		return productRepository.loadAllItems()
				.stream()
				.map(Product::toItemModel)
				.collect(Collectors.toList());
	}
}