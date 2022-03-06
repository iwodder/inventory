package com.wodder.inventory.models;

public class InventoryCountModel {
	private final String inventoryId;
	private final String productId;
	private int units;
	private int cases;

	public InventoryCountModel(String inventoryId, String productId, int units, int cases) {
		this.inventoryId = inventoryId;
		this.productId = productId;
		this.units = units;
		this.cases = cases;
	}

	public InventoryCountModel(String inventoryId, String productId) {
		this(inventoryId, productId, 0, 0);
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public String getProductId() {
		return productId;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}
}
