package com.wodder.inventory.models;

import java.io.*;

public class InventoryCountModel implements Serializable {
	private final String inventoryId;
	private final String productId;
	private double units;
	private double cases;

	public InventoryCountModel(String inventoryId, String productId, double units, double cases) {
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

	public double getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public double getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}
}
