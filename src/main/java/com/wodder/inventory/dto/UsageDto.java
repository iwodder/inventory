package com.wodder.inventory.dto;

public class UsageDto {
  private String unitsUsed;
  private String dollarsUsed;

  public String getUnitsUsed() {
    return unitsUsed;
  }

  public void setUnitsUsed(String unitsUsed) {
    this.unitsUsed = unitsUsed;
  }

  public String getDollarsUsed() {
    return dollarsUsed;
  }

  public void setDollarsUsed(String dollarsUsed) {
    this.dollarsUsed = dollarsUsed;
  }
}
