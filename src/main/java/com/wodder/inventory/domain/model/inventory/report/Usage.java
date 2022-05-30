package com.wodder.inventory.domain.model.inventory.report;

public class Usage {
  //Starting on-hand
  //Ending on-hand
  public static Usage none() {
    return new Usage();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public double getUnits() {
    return 0.5;
  }

  public double getDollars() {
    return 0.49;
  }
}
