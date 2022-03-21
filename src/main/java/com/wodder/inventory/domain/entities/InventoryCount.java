package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

public class InventoryCount {
	private double units;
	private double cases;

	public InventoryCount(double units, double cases) {
		setUnits(units);
		setCases(cases);
	}

	public InventoryCount(InventoryCount that) {
		this(that.getUnits(), that.getCases());
	}

	public InventoryCount(InventoryCountModel model) {
		this(model.getUnits(), model.getCases());
	}

	static InventoryCount countOfZero() {
		return new InventoryCount(0.0, 0.0);
	}

	public double getUnits() {
		return units;
	}

	public double getCases() {
		return cases;
	}

	private void setUnits(double count) {
		isGreaterThanZero(count);
		this.units = count;
	}

	private void setCases(double count) {
		isGreaterThanZero(count);
		this.cases = count;
	}

	private void isGreaterThanZero(double count) {
		if (count < 0.0) {
			throw new IllegalArgumentException();
		}
	}

}
