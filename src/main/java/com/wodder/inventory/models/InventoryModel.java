package com.wodder.inventory.models;

import java.io.*;
import java.time.*;
import java.util.*;

public class InventoryModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate inventoryDate;
	private final List<ItemModel> items;
	private final String state;
	private final String id;

	public InventoryModel(String id, String state) {
		this.id = id;
		this.state = state;
		items = new ArrayList<>();
	}

	public InventoryModel() {
		this("", "OPEN");
	}

	public void addInventoryItem(ItemModel itemModel) {
		items.add(itemModel);
	}

	public LocalDate getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public int numberOfItems() {
		return items.size();
	}

	public String getState() {
		return state;
	}

	public String getId() {
		return id;
	}

	public List<ItemModel> getItems() {
		return items;
	}
}
