package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import javax.annotation.*;
import javax.faces.view.*;
import javax.inject.*;
import java.io.*;
import java.time.format.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@Named
@ViewScoped
public class InventoryController implements Serializable {

	private transient InventoryService inventoryService;
	private transient ItemService itemService;
	private InventoryModel model;
	private Map<String, ProductModel> products;
	private Map<String, List<InventoryCountModel>> counts;

	@PostConstruct
	void init() {
		inventoryService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(InventoryService.class);
		itemService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ItemService.class);
		model = inventoryService.createInventory();
		products = itemService.loadAllActiveItems()
				.stream()
				.collect(Collectors.toMap(ProductModel::getId, Function.identity()));
		counts = products.values().stream()
				.collect(
						Collectors.groupingBy(
								ProductModel::getLocation,
								Collectors.mapping(p -> new InventoryCountModel(p.getId()), Collectors.toList())
						)
				);
	}

	public String getInventoryDate() {
		return model.getInventoryDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}

	public Map<String, List<InventoryCountModel>> getItemMap() {
		return counts;
	}

	public List<InventoryCountModel> getItems(String location) {
		return Collections.unmodifiableList(counts.getOrDefault(location, Collections.emptyList()));
	}

	public String getName(String productId) {
		return products.get(productId).getName();
	}

	public List<String> getLocations() {
		return new ArrayList<>(counts.keySet());
	}

	public void save() {
		List<InventoryCountModel> l = counts.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
		inventoryService.addInventoryCounts(model.getId(), l);
	}
}
