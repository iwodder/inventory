package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryCount {
	private final InventoryId inventoryId;
	private final ProductId productId;
	private int units;
	private int cases;

	public InventoryCount(InventoryId inventoryId, ProductId productId, int units, int cases) {
		this.inventoryId = inventoryId;
		this.productId = productId;
		setUnits(units);
		setCases(cases);
	}

	public InventoryCount(InventoryCount that) {
		this(that.getInventoryId(), that.getProductId(), that.getUnits(), that.getCases());
	}

	public InventoryCount(InventoryCountModel model) {
		this(
			InventoryId.inventoryIdOf(model.getInventoryId()), ProductId.productIdOf(model.getProductId()),
			model.getUnits(), model.getCases());
	}

	public InventoryId getInventoryId() {
		return inventoryId;
	}

	public ProductId getProductId() {
		return productId;
	}

	public int getUnits() {
		return units;
	}

	public int getCases() {
		return cases;
	}

	private void setUnits(int count) {
		isGreaterThanZero(count);
		this.units = count;
	}

	private void setCases(int count) {
		isGreaterThanZero(count);
		this.cases = count;
	}

	private void isGreaterThanZero(int count) {
		if (count < 0) {
			throw new IllegalArgumentException();
		}
	}

	public InventoryCountModel toModel() {
		return new InventoryCountModel(inventoryId.getId(), productId.getId(), units, cases);
	}
}
