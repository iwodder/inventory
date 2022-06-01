package com.wodder.inventory.domain.model.inventory.report;

import com.wodder.inventory.domain.model.inventory.OnHand;

public class Usage {

  private static final Usage NONE = new Usage(OnHand.zero(), OnHand.zero());
  //Starting on-hand
  private final OnHand starting;
  private final OnHand ending;
  //Ending on-hand

  private Usage(OnHand starting, OnHand ending) {
    this.starting = starting;
    this.ending = ending;
  }

  public static Usage none() {
    return NONE;
  }

  public static Usage of(OnHand start, OnHand end) {
    return new Usage(start, end);
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
    return starting.getUnitQty() - ending.getUnitQty();
  }

  public double getDollars() {
    return starting.getTotalDollars() - ending.getTotalDollars();
  }
}
