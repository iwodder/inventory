package com.wodder.inventory.domain.entities;

public class UnitOfMeasurement {
	private String unit;
	private final int itemsPerCase;

	public UnitOfMeasurement(String unit, int unitsPerCase) {
		setUnit(unit);
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

	private void setUnit(String unit) {
		if (unit == null || unit.isBlank()) {
			throw new IllegalArgumentException(String.format("Unit cannot be %s.", (unit == null) ? "null" : "blank"));
		}
		this.unit = unit;
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