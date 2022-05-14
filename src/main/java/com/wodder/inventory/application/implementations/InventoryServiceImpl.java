package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;

public class InventoryServiceImpl implements InventoryService {

	private final Repository<Inventory, InventoryId> repository;
	private final Repository<Product, ProductId> productRepository;

	InventoryServiceImpl(Repository<Inventory, InventoryId> repository, Repository<Product, ProductId> productRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
	}

	@Override
	public InventoryModel createInventory() {

		return repository.createItem(new Inventory()).toModel();
	}

	@Override
	public Optional<InventoryModel> addInventoryCount(String inventoryId, String productId, double units, double cases) {
		Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
		Optional<Product> productOpt = productRepository.loadById(ProductId.productIdOf(productId));
		if (opt.isPresent() && productOpt.isPresent()) {
			Inventory i = opt.get();
			InventoryItem item = InventoryItem.fromProduct(productOpt.get());
			i.addItemToInventory(item.updateCount(new InventoryCount(units, cases)));
			return Optional.of(i.toModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<InventoryModel> addInventoryCounts(String inventoryId, Collection<InventoryCountModel> counts) {
		Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
		if (opt.isPresent()) {
			Inventory i = opt.get();
			for (InventoryCountModel m : counts) {
				Product p = productRepository.loadById(ProductId.productIdOf(m.getProductId())).get();
//				i.updateInventoryCount(InventoryItem.fromProduct(p), new InventoryCount(m.getUnits(), m.getCases()));
			}
			return Optional.of(i.toModel());
		} else {
			return Optional.empty();
		}
	}
}