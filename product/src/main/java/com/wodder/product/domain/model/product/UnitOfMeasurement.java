package com.wodder.product.domain.model.product;

public class UnitOfMeasurement {
  private String unit;

  public UnitOfMeasurement(String unit) {
    setUnit(unit);
  }

  public UnitOfMeasurement(UnitOfMeasurement that) {
    this(that.unit);
  }

  public static UnitOfMeasurement empty() {
    return new UnitOfMeasurement("empty");
  }

  public static UnitOfMeasurement of(String unit) {
    return new UnitOfMeasurement(unit);
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

    UnitOfMeasurement that = (UnitOfMeasurement) o;
    return getUnit() != null ? getUnit().equalsIgnoreCase(that.getUnit()) : that.getUnit() == null;
  }

  @Override
  public int hashCode() {
    int result = getUnit() != null ? getUnit().hashCode() : 0;
    result = 31 * result;
    return result;
  }
}
