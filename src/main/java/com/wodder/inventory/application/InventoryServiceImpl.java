package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;

public class InventoryServiceImpl implements InventoryService {

	private final Repository<Inventory, InventoryId> repository;

	InventoryServiceImpl(Repository<Inventory, InventoryId> repository) {
		this.repository = repository;
	}

	@Override
	public InventoryModel createInventory() {
		return repository.createItem(new Inventory()).toModel();
	}

	@Override
	public Optional<InventoryModel> addInventoryCount(String inventoryId, String productId, double units, double cases) {
		Optional<Inventory> opt = repository.loadById(InventoryId.inventoryIdOf(inventoryId));
		if (opt.isPresent()) {
			Inventory i = opt.get();
			i.addInventoryCount(ProductId.productIdOf(productId), units, cases);
			return Optional.of(i.toModel());
		} else {
			return Optional.empty();
		}
	}
}
