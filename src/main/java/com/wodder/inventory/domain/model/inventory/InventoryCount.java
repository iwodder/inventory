package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.dto.InventoryCountDto;
import java.util.Objects;

public class InventoryCount {

  private static final InventoryCount ZERO = new InventoryCount(0.0, 0.0);
  private double units;
  private double cases;
  private ItemId itemId;

  public InventoryCount(double units, double cases) {
    setUnits(units);
    setCases(cases);
  }

  private InventoryCount(ItemId itemId, double units, double cases) {
    this.itemId = itemId;
    setCases(cases);
    setUnits(units);
  }

  public InventoryCount(InventoryCount that) {
    this(that.getUnits(), that.getCases());
  }

  public InventoryCount(InventoryCountDto model) {
    this(model.getUnits(), model.getCases());
  }

  public static InventoryCount ofZero() {
    return ZERO;
  }

  public static InventoryCount countFrom(String units, String cases) {
    return new InventoryCount(Double.parseDouble(units), Double.parseDouble(cases));
  }

  public static InventoryCount countFor(ItemId itemId) {
    return new InventoryCount(itemId, 0, 0);
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
    if (count < 0.0) {
      throw new IllegalArgumentException();
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
    InventoryCount that = (InventoryCount) o;
    return Double.compare(that.getUnits(), getUnits()) == 0
        && Double.compare(that.getCases(), getCases()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUnits(), getCases());
  }
}
