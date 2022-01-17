package com.wodder.inventory.domain.entities;

public class UnitOfMeasurement {
	private final String unit;
	private final int itemsPerCase;

	public UnitOfMeasurement(String unit, int unitsPerCase) {
		this.unit = unit;
		this.itemsPerCase = unitsPerCase;
	}

	public UnitOfMeasurement(String unit) {
		this(unit, 0);
	}

	public String getUnit() {
		return unit;
	}

	public int getItemsPerCase() {
		return itemsPerCase;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UnitOfMeasurement that = (UnitOfMeasurement) o;

		if (getItemsPerCase() != that.getItemsPerCase()) return false;
		return getUnit() != null ? getUnit().equals(that.getUnit()) : that.getUnit() == null;
	}

	@Override
	public int hashCode() {
		int result = getUnit() != null ? getUnit().hashCode() : 0;
		result = 31 * result + getItemsPerCase();
		return result;
	}
}
