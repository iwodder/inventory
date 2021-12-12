package com.wodder.inventory.models;

public class InventoryCountModel {

	private final long itemId;
	private final String name;
	private final String category;
	private final int onHandQty;
	private int count;

	public InventoryCountModel(long itemId, String name, String category, int currentOnHand) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.onHandQty = currentOnHand;
		this.count = 0;
	}

	public long getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public int getOnHandQty() {
		return onHandQty;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
