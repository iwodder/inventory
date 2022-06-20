package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.dto.InventoryCountDto;
import java.util.Objects;

public class Count {

  private static final Count ZERO = new Count(0.0, 0.0);
  private double units;
  private double cases;
  private ItemId itemId;

  private Count(double units, double cases) {
    setUnits(units);
    setCases(cases);
  }

  private Count(ItemId itemId, double units, double cases) {
    this.itemId = itemId;
    setCases(cases);
    setUnits(units);
  }

  public Count(Count that) {
    this(that.itemId, that.getUnits(), that.getCases());
  }

  public Count(InventoryCountDto model) {
    this(model.getUnits(), model.getCases());
  }

  public static Count ofZero() {
    return ZERO;
  }

  public static Count countFrom(String units, String cases) {
    return new Count(Double.parseDouble(units), Double.parseDouble(cases));
  }

  public static Count copy(Count other) {
    return new Count(other);
  }

  public double getUnits() {
    return units;
  }

  private void setUnits(double count) {
    isGreaterThanZero(count);
    this.units = count;
  }

  public double getCases() {
    return cases;
  }

  private void setCases(double count) {
    isGreaterThanZero(count);
    this.cases = count;
  }

  private void isGreaterThanZero(double count) {
    if (Double.compare(count, 0) > -1) {
      throw new IllegalArgumentException(
          "Count cannot have a negative value, was supplied " + count);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Count that = (Count) o;
    return Double.compare(that.getUnits(), getUnits()) == 0
        && Double.compare(that.getCases(), getCases()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUnits(), getCases());
  }
}
