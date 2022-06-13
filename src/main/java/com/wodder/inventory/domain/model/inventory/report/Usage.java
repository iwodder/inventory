package com.wodder.inventory.domain.model.inventory.report;

import com.wodder.inventory.domain.model.inventory.OnHand;
import com.wodder.inventory.dto.UsageDto;

public class Usage {

  private static final Usage NONE = new Usage(OnHand.zero(), OnHand.zero());

  private final OnHand starting;
  private final OnHand ending;
  private final Integer receivedQty;

  private Usage(OnHand starting, OnHand ending) {
    this(starting, ending, 0);
  }

  private Usage(OnHand starting, OnHand ending, Integer quantity) {
    this.starting = starting;
    this.ending = ending;
    this.receivedQty = quantity;
  }

  public static Usage none() {
    return NONE;
  }

  public static Usage of(OnHand start, OnHand end) {
    return new Usage(start, end);
  }

  public Usage addReceivedQty(Integer quantity) {
    return new Usage(this.starting, this.ending, quantity);
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
    return (starting.getUnitQty() + receivedQty) - ending.getUnitQty();
  }

  public double getDollars() {
    return starting.getTotalDollars()
            + (receivedQty * starting.getPrice().getUnitPrice().doubleValue())
            - ending.getTotalDollars();
  }

  public UsageDto toDto() {
    UsageDto dto = new UsageDto();
    dto.setUnitsUsed(Double.toString(getUnits()));
    dto.setUnitsUsed(Double.toString(getDollars()));
    return dto;
  }
}
