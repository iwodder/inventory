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
	private Map<String, List<InventoryCountModel>> items;

	@PostConstruct
	void init() {
		inventoryService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(InventoryService.class);
		itemService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ItemService.class);
		model = inventoryService.createInventory();
		products = itemService.loadAllActiveItems()
				.stream()
				.collect(Collectors.toMap(ProductModel::getId, Function.identity()));
		items = itemService.loadAllActiveItems().stream()
				.collect(HashMap::new,
						(map, val) -> {
							if (map.containsKey(val.getLocation())) {
								List<InventoryCountModel> l = map.get(val.getLocation());
								InventoryCountModel m = new InventoryCountModel(model.getId(), val.getId());
								l.add(m);
								model.addInventoryCountModel(m);
							} else {
								List<InventoryCountModel> l = new ArrayList<>();
								InventoryCountModel m = new InventoryCountModel(model.getId(), val.getId());
								l.add(m);
								model.addInventoryCountModel(m);
								map.put(val.getLocation(), l);
							}
						},
						(map1, map2) -> {
							map2.forEach((k,v) -> {
								map1.merge(k, v, (v1, v2) -> { v1.addAll(v2); return v1; });
							});
				});
	}

	public String getInventoryDate() {
		return model.getInventoryDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}

	public Map<String, List<InventoryCountModel>> getItemMap() {
		return items;
	}

	public List<InventoryCountModel> getItems(String location) {
		return Collections.unmodifiableList(items.getOrDefault(location, Collections.emptyList()));
	}

	public String getName(String productId) {
		return products.get(productId).getName();
	}

	public List<String> getLocations() {
		return new ArrayList<>(items.keySet());
	}

	public void save() {
		System.out.println("Saved");
	}
}
