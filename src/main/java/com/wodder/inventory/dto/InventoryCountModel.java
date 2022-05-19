package com.wodder.inventory.dto;

import java.io.*;

public class InventoryCountModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String productId;
	private double units;
	private double cases;

	public InventoryCountModel(double units, double cases) {
		this.units = units;
		this.cases = cases;
	}

	public InventoryCountModel(String productId) {
		this.productId = productId;
		this.units = 0.0;
		this.cases = 0.0;
	}

	public InventoryCountModel(String productId, double units, double cases) {
		this.productId = productId;
		this.units = units;
		this.cases = cases;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public double getUnits() {
		return units;
	}

	public void setUnits(double units) {
		this.units = units;
	}

	public double getCases() {
		return cases;
	}

	public void setCases(double cases) {
		this.cases = cases;
	}
}
