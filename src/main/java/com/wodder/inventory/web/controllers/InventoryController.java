package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.*;
import com.wodder.inventory.application.implementations.*;
import com.wodder.inventory.dto.*;
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
	private static final long serialVersionUID = 1L;
	private transient InventoryService inventoryService;
	private transient ProductService productService;
	private InventoryDto model;
	private Map<String, List<InventoryItemDto>> items;

	@PostConstruct
	void init() {
		inventoryService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(InventoryService.class);
		productService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ProductService.class);
		model = inventoryService.createInventory();
		items = model.getItems()
				.stream()
				.collect(Collectors.groupingBy(InventoryItemDto::getLocation, Collectors.toList()));
	}

	public String getInventoryDate() {
		return model.getInventoryDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}

	public Map<String, List<InventoryItemDto>> getItemMap() {
		return items;
	}

	public List<InventoryItemDto> getItems(String location) {
		return Collections.unmodifiableList(items.getOrDefault(location, Collections.emptyList()));
	}

	public List<String> getLocations() {
		return new ArrayList<>(items.keySet());
	}

	public void save() {
		inventoryService.saveInventory(model);
	}
}
