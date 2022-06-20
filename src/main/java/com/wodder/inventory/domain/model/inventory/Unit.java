package com.wodder.inventory.domain.model.inventory;

public class Unit {

  private String unit;

  private Unit(String unit) {
    setUnit(unit);
  }

  public static Unit empty() {
    return new Unit("NONE");
  }

  public static Unit of(String unit) {
    return new Unit(unit);
  }

  public String getUnit() {
    return unit;
  }

  private void setUnit(String unit) {
    if (unit == null || unit.isBlank()) {
      throw new IllegalArgumentException(
          String.format("Unit cannot be %s.", (unit == null) ? "null" : "blank"));
    }
    this.unit = unit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Unit that = (Unit) o;
    return getUnit() != null ? getUnit().equalsIgnoreCase(that.getUnit()) : that.getUnit() == null;
  }

  @Override
  public int hashCode() {
    return getUnit() != null ? getUnit().hashCode() : 0;
  }
}
