package com.wodder.product.application;

public class UpdateUnitsCommand {

  private String units;

  public void setUnits(String units) {
    this.units = units;
  }

  public String getUnits() {
    return units;
  }

  @Override
  public String toString() {
    return "UpdateUnitsCommand{units='" + units + '\'' + '}';
  }
}
