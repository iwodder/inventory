package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.dto.InventoryCountModel;

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

  public static InventoryCount countOfZero() {
    return new InventoryCount(0.0, 0.0);
  }

  public static InventoryCount countFrom(String units, String cases) {
    return new InventoryCount(Double.parseDouble(units), Double.parseDouble(cases));
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
}
