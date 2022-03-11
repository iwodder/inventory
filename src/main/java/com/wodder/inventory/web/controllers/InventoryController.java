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
import java.util.stream.*;

@Named
@ViewScoped
public class InventoryController implements Serializable {

	private transient InventoryService inventoryService;
	private transient ItemService itemService;
	private InventoryModel model;
	private Map<String, List<ProductModel>> items;

	@PostConstruct
	void init() {
		inventoryService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(InventoryService.class);
		itemService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ItemService.class);
		model = inventoryService.createInventory();
		items = itemService.loadAllActiveItems().stream()
				.collect(Collectors.groupingBy(ProductModel::getCategory, Collectors.toList()));
	}

	public String getInventoryDate() {
		return model.getInventoryDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}

	public Map<String, List<ProductModel>> getItems() {
		return items;
	}

	public List<String> getCategories() {
		return new ArrayList<>(items.keySet());
	}
}
